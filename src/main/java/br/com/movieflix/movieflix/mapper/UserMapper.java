package br.com.movieflix.movieflix.mapper;

import br.com.movieflix.movieflix.controller.request.UserRequest;
import br.com.movieflix.movieflix.controller.response.UserMeResponse;
import br.com.movieflix.movieflix.controller.response.UserResponse;
import br.com.movieflix.movieflix.entity.Role;
import br.com.movieflix.movieflix.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {
    public static User toUser(UserRequest userRequest){
        return User.builder()
                .email(userRequest.email())
                .password(userRequest.password())
                .name(userRequest.name())
                .build();

    }

    public static UserMeResponse toUserMeResponse(User user){
        return UserMeResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .money(user.getMoney())
                .role(user.getRoles())
                .build();
    }

    public static UserResponse toUserResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .roles(user.getRoles())
                .money(user.getMoney())
                .build();
    }
}
