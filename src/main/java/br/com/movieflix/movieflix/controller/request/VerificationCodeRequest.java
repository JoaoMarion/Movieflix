package br.com.movieflix.movieflix.controller.request;

import lombok.Builder;

@Builder
public record VerificationCodeRequest(int code) {
}
