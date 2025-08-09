package com.codeera.expensetracker.mapper;


import com.codeera.expensetracker.dto.Registration.UserRegisterRequestDto;
import com.codeera.expensetracker.dto.Registration.UserResponseDto;
import com.codeera.expensetracker.entity.User;

public class UserMapper {

    public static User mapToUserEntity(UserRegisterRequestDto userRegisterRequestDto) {
        User user = new User();
        user.setName(userRegisterRequestDto.getUsername());
        user.setEmail(userRegisterRequestDto.getEmail());
        user.setPassword(userRegisterRequestDto.getPassword());
        return user;
    }

    public static UserResponseDto mapToUserResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setUsername(user.getName());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setRole(user.getRoles().iterator().next().getName());
        return userResponseDto;
    }


}
