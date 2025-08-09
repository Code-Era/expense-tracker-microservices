package com.expense_category_service.configSecuirty;

import com.expense_category_service.authExceptionHandler.CustomAccessDeniedHandler;
import com.expense_category_service.authExceptionHandler.CustomBasicAuthenticationEntryPoint;
import com.expense_category_service.jwtutil.JWTTokenValidatorFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class ProjectSecurityConfiguration {


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                                auth.requestMatchers("/v1/expense/categories/**").hasRole("ADMIN")
                                .requestMatchers("/v1/expense/expenses/**").authenticated()

                );

        http.exceptionHandling(ex -> ex
                .authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint(new ObjectMapper()))
                .accessDeniedHandler(new CustomAccessDeniedHandler(new ObjectMapper())));
        return http.build();
    }
}
