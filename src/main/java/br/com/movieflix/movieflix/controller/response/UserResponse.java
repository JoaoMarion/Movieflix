package br.com.movieflix.movieflix.controller.response;

import br.com.movieflix.movieflix.entity.Role;
import lombok.Builder;

import java.util.List;

@Builder
public record UserResponse(String name,
                           String email,
                           Long id,
                           List<Role> roles
) {
}
