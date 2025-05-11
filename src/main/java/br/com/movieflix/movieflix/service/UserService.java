package br.com.movieflix.movieflix.service;

import br.com.movieflix.movieflix.codeGenerator.VerificationCodeGenerator;
import br.com.movieflix.movieflix.controller.response.UserResponse;
import br.com.movieflix.movieflix.entity.Role;
import br.com.movieflix.movieflix.entity.User;
import br.com.movieflix.movieflix.entity.embedded.VerificationCode;
import br.com.movieflix.movieflix.repository.RoleRepository;
import br.com.movieflix.movieflix.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final IdentifierGeneratorService identifierGeneratorService;

    private final VerificationCodeGenerator verificationCodeGenerator;

    public User save(User user){
        Role userRole = roleRepository.findByName("user")
                .orElseThrow(() -> new RuntimeException("Role 'user' não encontrada"));
            String password = user.getPassword();
            user.setPassword(passwordEncoder.encode(password));
            user.setRoles(List.of(userRole));
            user.setIdentifier(identifierGeneratorService.generateUniqueIdentifier());
            user.setMoney(BigDecimal.ZERO);
            user.setVerificationCode(
                    VerificationCode.builder()
                            .code(verificationCodeGenerator.generateCode())
                            .expireAt(LocalDateTime.now().plusMinutes(10))
                            .build()
            );
            return userRepository.save(user);
    }

    public User saveNewVerificationCode(User user){
        user.setVerificationCode(
                VerificationCode.builder()
                        .code(verificationCodeGenerator.generateCode())
                        .expireAt(LocalDateTime.now().plusMinutes(10))
                        .build()
        );
        return userRepository.save(user);
    }

    public User saveUpdate(User user){
        return userRepository.save(user);
    }

    public Optional<UserDetails> findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

    public Optional<UserDetails> findByVerificationCode(int code){
        return userRepository.findByVerificationCode_Code(code);
    }


    public List<User> findAll(){
      return userRepository.findAll();
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }


}
