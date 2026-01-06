package com.personal.web.mappers;

import com.personal.web.dtos.RegisterDto;
import com.personal.web.dtos.UserDto;
import com.personal.web.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(RegisterDto req){
        return User.builder()
                .username(req.username())
                .password(req.password())
                .build();
    }

    public UserDto toDtoResponse(User user){
        return new UserDto(
                user.getId(),
                user.getUsername()
        );
    }
}
