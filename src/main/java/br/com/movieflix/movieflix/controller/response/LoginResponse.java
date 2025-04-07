package br.com.movieflix.movieflix.controller.response;

import lombok.Builder;

@Builder
public record LoginResponse(String token) {
}
