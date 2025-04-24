package br.com.movieflix.movieflix.mapper;

import br.com.movieflix.movieflix.controller.request.RoleRequest;
import br.com.movieflix.movieflix.controller.response.RoleResponse;
import br.com.movieflix.movieflix.entity.Role;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RoleMapper {
    public static Role toRole(RoleRequest request){
       return Role.builder()
               .name(request.name())
               .description(request.description())
               .build();
    }

    public static RoleResponse toRoleResponse(Role role){
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .build();
    }
}
