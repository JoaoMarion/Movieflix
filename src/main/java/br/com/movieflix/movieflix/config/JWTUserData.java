package br.com.movieflix.movieflix.config;


import br.com.movieflix.movieflix.entity.Role;
import lombok.Builder;

import java.util.List;


@Builder
public record JWTUserData(Long id, String name, String email, List<Role> roles) {
}

