package com.authuserservice.service.User;


import com.authuserservice.dto.Registration.UserRegisterRequestDto;
import com.authuserservice.dto.Registration.UserResponseDto;

public interface UserService {

    UserResponseDto saveUser(UserRegisterRequestDto userRegisterRequestDto);
    UserResponseDto getUserByEmail(String email);

    long getAlluserCount();


}
