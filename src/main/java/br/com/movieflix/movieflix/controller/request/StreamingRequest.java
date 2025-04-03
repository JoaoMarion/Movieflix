package br.com.movieflix.movieflix.controller.request;

import lombok.Builder;

@Builder
public record StreamingRequest(String name) {
}
