package br.com.movieflix.movieflix.controller.response;

import br.com.movieflix.movieflix.entity.Role;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record UserMeResponse(String name, String email, BigDecimal money, List<Role> role) {
}
