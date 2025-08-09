package com.authuserservice.service.User;



import com.authuserservice.dto.Registration.UserRegisterRequestDto;
import com.authuserservice.dto.Registration.UserResponseDto;
import com.authuserservice.entity.Role;
import com.authuserservice.entity.User;
import com.authuserservice.exception.ExceptionUtil;
import com.authuserservice.mapper.UserMapper;
import com.authuserservice.repository.RoleRepository;
import com.authuserservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder   mPasswordEncoder;

    @Override
    public UserResponseDto saveUser(UserRegisterRequestDto userRegisterRequestDto) {

        userRepository.findByEmail(userRegisterRequestDto.getEmail()).ifPresent(
                user -> ExceptionUtil.duplicate("User ", userRegisterRequestDto.getEmail())
        );

       String requestedRoles = "ROLE_"+userRegisterRequestDto.getRole().toUpperCase();
       Role role = roleRepository.findByName(requestedRoles)
                .orElseThrow(() -> new RuntimeException(requestedRoles + " not found"));


       User user = UserMapper.mapToUserEntity(userRegisterRequestDto);
       user.setPassword(mPasswordEncoder.encode(user.getPassword()));
       user.setRoles(Set.of(role));

       User savedUser = userRepository.save(user);
        if(savedUser.getId() <=0)
             ExceptionUtil.throwSaveFailed("user");

       return UserMapper.mapToUserResponseDto(savedUser);
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
       /* User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        ExceptionUtil.throwResourceNotFound("User not found: " , email));

        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());  // make sure UserResponseDto has id field
        dto.setEmail(user.getEmail());
        return dto;*/
        return null;
    }

    @Override
    public long getAlluserCount() {
        return userRepository.countUsersByRoleName("ROLE_USER");
    }
}
