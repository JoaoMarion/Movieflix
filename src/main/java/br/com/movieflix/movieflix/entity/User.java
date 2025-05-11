package br.com.movieflix.movieflix.entity;

import br.com.movieflix.movieflix.entity.embedded.RefreshToken;
import br.com.movieflix.movieflix.entity.embedded.VerificationCode;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "identifier", unique = true)
    private UUID identifier;

    @Column(name = "money", precision = 10, scale = 4)
    private BigDecimal money = BigDecimal.ZERO;

    @Column(name = "enabled")
    private boolean enabled;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "code", column = @Column(name = "verification_code", nullable = true)),
            @AttributeOverride(name = "expireAt", column = @Column(name = "verification_code_expire_at", nullable = true))
    })
    private VerificationCode verificationCode;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "refreshToken", column = @Column(name = "refresh_token", nullable = true)),
            @AttributeOverride(name = "expireAt", column = @Column(name = "refresh_token_expire_at", nullable = true))
    })
    private RefreshToken refreshToken;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()))
                .toList();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
