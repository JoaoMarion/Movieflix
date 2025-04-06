package br.com.movieflix.movieflix.mapper;

import br.com.movieflix.movieflix.controller.request.UserRequest;
import br.com.movieflix.movieflix.controller.response.UserResponse;
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

    public static UserResponse toUserResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
