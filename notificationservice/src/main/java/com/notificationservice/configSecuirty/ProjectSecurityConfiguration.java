package com.notificationservice.configSecuirty;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.notificationservice.authExceptionHandler.CustomAccessDeniedHandler;
import com.notificationservice.authExceptionHandler.CustomBasicAuthenticationEntryPoint;
import com.notificationservice.jwtutil.JWTTokenValidatorFilter;
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
                        auth.requestMatchers("/v1/notifications/**").authenticated()
                );

        http.exceptionHandling(ex -> ex
                .authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint(new ObjectMapper()))
                .accessDeniedHandler(new CustomAccessDeniedHandler(new ObjectMapper())));
        return http.build();
    }
}
