package br.com.movieflix.movieflix.config;

import br.com.movieflix.movieflix.entity.Role;
import br.com.movieflix.movieflix.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
public class TokenService {

    @Value("${movieflix.security.secret}")
    private String secret;

    public String generateToken(User user){

        Algorithm algorithm = Algorithm.HMAC256(secret);

        List<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .toList();

        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("userId", user.getId())
                .withClaim("userName", user.getName())
                .withClaim("userRoles", roleNames)
                .withExpiresAt(Instant.now().plusSeconds(3600))
                .withIssuedAt(Instant.now())
                .withIssuer("MovieFlix")
                .sign(algorithm);

    }

    public Optional<JWTUserData> verifyToken(String token) {
        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);

            DecodedJWT jwt = JWT.require(algorithm)
                    .build()
                    .verify(token);

            List<String> roleNames = jwt.getClaim("userRoles").asList(String.class);
            List<Role> roles = roleNames.stream()
                    .map(name -> Role.builder().name(name).build())
                    .toList();

            return Optional.of(JWTUserData
                    .builder()
                    .id(jwt.getClaim("userId").asLong())
                    .name(jwt.getClaim("name").asString())
                    .roles(roles)
                    .email(jwt.getSubject())
                    .build());

        } catch (JWTVerificationException ex) {
            return Optional.empty();
        }


    }

}
