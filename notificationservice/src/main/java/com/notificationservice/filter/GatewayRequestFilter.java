package com.notificationservice.filter;


import com.notificationservice.payload.ApiResponse;
import com.notificationservice.payload.ErrorModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class GatewayRequestFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    public GatewayRequestFilter() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("X-GATEWAY-ACCESS");

        if (!"true".equalsIgnoreCase(header)) {

            ErrorModel error = new ErrorModel();
            error.setApiPath(request.getRequestURI());
            error.setErrorCode(HttpStatus.FORBIDDEN);
            error.setErrorMessage("Access Denied: Not from API Gateway");

            ApiResponse<Object> apiResponse = new ApiResponse<>();
            apiResponse.setError(error);
            apiResponse.setStatusCode(String.valueOf(HttpStatus.FORBIDDEN.value()));
            apiResponse.setStatusMsg("Access Denied");

            String json = objectMapper.writeValueAsString(apiResponse);
            response.getWriter().write(json);
            return;
        }

        filterChain.doFilter(request, response);
    }
}