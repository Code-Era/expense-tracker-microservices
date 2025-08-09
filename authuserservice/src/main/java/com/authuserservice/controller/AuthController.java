package com.authuserservice.controller;


import com.authuserservice.constant.AppConstants;
import com.authuserservice.constant.ResponseConstants;
import com.authuserservice.dto.Login.LoginRequestDto;
import com.authuserservice.dto.Login.LoginResponseDto;
import com.authuserservice.dto.Registration.UserRegisterRequestDto;
import com.authuserservice.dto.Registration.UserResponseDto;
import com.authuserservice.jwtutil.JwtTokenGenerator;
import com.authuserservice.payload.ApiResponse;
import com.authuserservice.payload.ResponseService;
import com.authuserservice.service.User.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {


    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenGenerator jwtTokenGenerator;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDto>> registerUser(
            @Valid @RequestBody UserRegisterRequestDto userRegisterRequestDto){

        UserResponseDto userResponseDto = userService.saveUser(userRegisterRequestDto);

        return ResponseService.buildSuccessResponse(userResponseDto,
                HttpStatus.CREATED.value(), ResponseConstants.SUCCESS_REGISTRATION);
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> loginUser(
            @Valid @RequestBody LoginRequestDto loginRequestDto){

        Authentication authentication = UsernamePasswordAuthenticationToken
                .unauthenticated(loginRequestDto.getEmail(),loginRequestDto.getPassword());

        /**
         * custom Validation
         */
        Authentication authResponse =authenticationManager.authenticate(authentication );


        if(authResponse != null && authResponse.isAuthenticated()) {

           String jwt = jwtTokenGenerator.generateToken(authResponse);

            if(jwt != null && !jwt.isEmpty()) {
                LoginResponseDto loginResponseDto = new LoginResponseDto();
                loginResponseDto.setEmail(authResponse.getName());
                loginResponseDto.setToken(jwt);

                HttpHeaders headers = new HttpHeaders();
                headers.add(AppConstants.JWT_HEADER, jwt);
                return ResponseService.buildSuccessResponseHeader(headers, loginResponseDto,
                        HttpStatus.OK.value(), ResponseConstants.SUCCESS_LOGIN);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getUserCounts")
    public ResponseEntity<ApiResponse<Long>> getAllUsersCount() {
        return ResponseService.buildSuccessResponse(userService.getAlluserCount(),
                HttpStatus.OK.value(), "Users Fetched Successfully");
    }

}
