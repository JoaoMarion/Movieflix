package br.com.movieflix.movieflix.controller.request;

import lombok.Builder;

@Builder
public record CategoryRequest(String name) {
}
