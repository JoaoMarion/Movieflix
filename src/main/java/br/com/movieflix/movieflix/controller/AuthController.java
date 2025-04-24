package br.com.movieflix.movieflix.controller;

import br.com.movieflix.movieflix.config.TokenService;
import br.com.movieflix.movieflix.controller.request.LoginRequest;
import br.com.movieflix.movieflix.controller.request.UserRequest;
import br.com.movieflix.movieflix.controller.response.LoginResponse;
import br.com.movieflix.movieflix.controller.response.UserResponse;
import br.com.movieflix.movieflix.entity.User;
import br.com.movieflix.movieflix.exception.EmailAlreadyExist;
import br.com.movieflix.movieflix.exception.UsernameOrPasswordInvalidException;
import br.com.movieflix.movieflix.mapper.UserMapper;
import br.com.movieflix.movieflix.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/movieflix/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest request) {
        User user = UserMapper.toUser(request);

        if (userService.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailAlreadyExist("Já existe um Usuario com esse email");
        }
        User savedUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserMapper.toUserResponse(savedUser));

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try{
            UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(request.email(), request.password());
            Authentication authenticaticate = authenticationManager.authenticate(userAndPass);

            User user = (User) authenticaticate.getPrincipal();
            String token = tokenService.generateToken(user);

            return ResponseEntity.ok(new LoginResponse(token));

        } catch (Exception e){
            throw  new UsernameOrPasswordInvalidException("Usuario ou senha invalidos.");
        }



    }


}
