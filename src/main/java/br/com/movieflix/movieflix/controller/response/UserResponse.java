package br.com.movieflix.movieflix.controller.response;

import lombok.Builder;

@Builder
public record UserResponse(String name,
                           String email,
                           Long id) {
}
