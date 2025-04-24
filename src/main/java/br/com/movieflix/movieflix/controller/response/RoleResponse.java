package br.com.movieflix.movieflix.controller.response;

import lombok.Builder;

@Builder
public record RoleResponse(Long id, String name, String description) {
}
