package br.com.movieflix.movieflix.repository;

import br.com.movieflix.movieflix.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<UserDetails> findUserByEmail(String email);
    boolean existsByIdentifier(UUID identifier);
    Optional<UserDetails> findByVerificationCode_Code(int code);
    Optional<UserDetails> findByRefreshToken_RefreshToken(String refreshToken);

}
