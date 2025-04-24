package br.com.movieflix.movieflix.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RoleRequest(
        @NotBlank(message = "Role não pode ser vazia")
        String name,
        String description
) {
}
