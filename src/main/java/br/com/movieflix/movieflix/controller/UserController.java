package br.com.movieflix.movieflix.controller;


import br.com.movieflix.movieflix.config.JWTUserData;
import br.com.movieflix.movieflix.controller.response.UserMeResponse;
import br.com.movieflix.movieflix.controller.response.UserResponse;
import br.com.movieflix.movieflix.entity.User;
import br.com.movieflix.movieflix.exception.UserNotFound;
import br.com.movieflix.movieflix.mapper.UserMapper;
import br.com.movieflix.movieflix.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/movieflix/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll(){
        return ResponseEntity.ok(userService.findAll().stream()
                .map(UserMapper::toUserResponse)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id){
        return userService.findById(id)
                .map(user -> ResponseEntity.ok(UserMapper.toUserResponse(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
       return userService.findById(id)
               .map(user -> {
                   userService.delete(user.getId());
                   return ResponseEntity.noContent().build();
               })
               .orElseThrow(()-> new UserNotFound("Usuário não Encontrado"));
    }

    @GetMapping("/me")
    public ResponseEntity<UserMeResponse> getCurrentUser(Authentication authentication){
        JWTUserData userData = (JWTUserData) authentication.getPrincipal();
       return userService.findByEmail(userData.email())
                .map( ud -> ResponseEntity.ok(UserMapper.toUserMeResponse((User) ud))
                )
               .orElseThrow( ()-> new UserNotFound("Token Inválido")  );

    }
}


