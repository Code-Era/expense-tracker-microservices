package com.authuserservice.authExceptionHandler;


import com.authuserservice.payload.ApiResponse;
import com.authuserservice.payload.ErrorModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * AuthenticationEntryPoint implementation for basic authentication
 */
public class CustomBasicAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    // Constructor injection of ObjectMapper
    public CustomBasicAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        // Enable pretty-printing for JSON response
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        String message =  HttpStatus.UNAUTHORIZED.getReasonPhrase();

        response.setHeader("ExpanseTracker-error-reason", "Authentication failed");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=utf-8");

        ErrorModel error = new ErrorModel();
        error.setApiPath(request.getRequestURI());
        error.setErrorCode(HttpStatus.UNAUTHORIZED);
        error.setErrorMessage("Authentication failed");
       // error.setErrorTime(LocalDateTime.now());

        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setError(error);
        apiResponse.setStatusCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        apiResponse.setStatusMsg("Authentication failed");

        String json = objectMapper.writeValueAsString(apiResponse);
        response.getWriter().write(String.valueOf(json));
    }
}
