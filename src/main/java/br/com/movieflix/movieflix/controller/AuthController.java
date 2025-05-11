package br.com.movieflix.movieflix.controller;

import br.com.movieflix.movieflix.config.TokenService;
import br.com.movieflix.movieflix.controller.request.LoginRequest;
import br.com.movieflix.movieflix.controller.request.RefreshRequest;
import br.com.movieflix.movieflix.controller.request.UserRequest;
import br.com.movieflix.movieflix.controller.request.VerificationCodeRequest;
import br.com.movieflix.movieflix.controller.response.LoginResponse;
import br.com.movieflix.movieflix.controller.response.UserResponse;
import br.com.movieflix.movieflix.controller.response.VerificationCodeResponse;
import br.com.movieflix.movieflix.entity.User;
import br.com.movieflix.movieflix.entity.embedded.RefreshToken;
import br.com.movieflix.movieflix.exception.EmailAlreadyExist;
import br.com.movieflix.movieflix.exception.UsernameOrPasswordInvalidException;
import br.com.movieflix.movieflix.mapper.UserMapper;
import br.com.movieflix.movieflix.mapper.VerificationCodeMapper;
import br.com.movieflix.movieflix.repository.UserRepository;
import br.com.movieflix.movieflix.service.EmailService;
import br.com.movieflix.movieflix.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/movieflix/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final EmailService emailService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest request) {
        return userService.findByEmail(request.email())
                .map(userDetails -> {
                    User existingUser = (User) userDetails;

                    if (!existingUser.isEnabled()) {
                        userService.saveNewVerificationCode(existingUser);
                        try {
                            emailService.sendVerificationEmail(existingUser.getEmail(), existingUser.getVerificationCode().getCode());
                        } catch (MessagingException e) {
                            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .body(UserMapper.toUserResponse(existingUser));
                        }
                        throw new EmailAlreadyExist("Email já cadastrado. Novo código de confirmação enviado.");
                    }

                    throw new EmailAlreadyExist("Já existe um usuário com esse email.");
                })
                .orElseGet(() -> {
                    User newUser = UserMapper.toUser(request);
                    User savedUser = userService.save(newUser);
                    try {
                        emailService.sendVerificationEmail(savedUser.getEmail(), savedUser.getVerificationCode().getCode());
                    } catch (MessagingException e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(UserMapper.toUserResponse(savedUser));
                    }
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(UserMapper.toUserResponse(savedUser));
                });
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try{
            UsernamePasswordAuthenticationToken userAndPass =
                    new UsernamePasswordAuthenticationToken(request.email(), request.password());

            Authentication authenticaticate = authenticationManager.authenticate(userAndPass);

            User user = (User) authenticaticate.getPrincipal();
            String token = tokenService.generateToken(user);
            String refreshToken = tokenService.generateRefreshToken(user);

            user.setRefreshToken(
                    RefreshToken.builder()
                            .refreshToken(refreshToken)
                            .expireAt(LocalDateTime.now().plusDays(7))
                            .build()
            );

            userService.saveUpdate(user);

            return ResponseEntity.ok(new LoginResponse(token, refreshToken));

        } catch (DisabledException e) {
            throw new DisabledException("Usuario não Confirmado");
        }
        catch (BadCredentialsException e){
            throw  new UsernameOrPasswordInvalidException("Usuario ou senha invalidos.");
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<VerificationCodeResponse> verifyAccount(@RequestBody VerificationCodeRequest request) {
        return userService.findByVerificationCode(VerificationCodeMapper.toVerificationCode(request).getCode())
                .map(userDetails -> {
                    User user = (User) userDetails;

                    if (user.getVerificationCode().getExpireAt().isBefore(LocalDateTime.now())) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new VerificationCodeResponse("Código de verificação expirado."));
                    }

                    try {
                        user.setEnabled(true);
                        user.setVerificationCode(null);
                        userService.saveUpdate(user);

                        return ResponseEntity.ok(new VerificationCodeResponse("Usuário confirmado com sucesso."));
                    } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new VerificationCodeResponse("Erro ao confirmar usuário. Tente novamente."));
                    }
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new VerificationCodeResponse("Código de verificação inválido.")));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestBody RefreshRequest request) {

        UserDetails userDetails = userService.findByRefreshToken(request.refreshToken())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token inválido."));

        User user = (User) userDetails;

        if (user.getRefreshToken().getExpireAt().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token expirado.");
        }


        String newAccessToken = tokenService.generateToken(user);
        String newRefreshToken = tokenService.generateRefreshToken(user);

        user.setRefreshToken(RefreshToken.builder()
                .refreshToken(newRefreshToken)
                .expireAt(LocalDateTime.now().plusDays(7))
                .build());

        userService.saveUpdate(user);


        return ResponseEntity.ok(new LoginResponse(newAccessToken, newRefreshToken));
    }


}
