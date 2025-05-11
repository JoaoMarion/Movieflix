package br.com.movieflix.movieflix.controller.request;

import lombok.Builder;

@Builder
public record RefreshRequest(String refreshToken) {
}
