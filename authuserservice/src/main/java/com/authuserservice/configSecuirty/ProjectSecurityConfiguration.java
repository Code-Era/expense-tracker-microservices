package com.authuserservice.configSecuirty;


import com.authuserservice.authExceptionHandler.CustomAccessDeniedHandler;
import com.authuserservice.authExceptionHandler.CustomBasicAuthenticationEntryPoint;
import com.authuserservice.filter.GatewayRequestFilter;
import com.authuserservice.jwtutil.JWTTokenValidatorFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class ProjectSecurityConfiguration {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())// Login aur Register ko CSRF se exempt karo
                //.addFilterBefore(new GatewayRequestFilter(), JWTTokenValidatorFilter.class)
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .sessionManagement(s ->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        /*.requestMatchers("/swagger-ui/**", "/v3/api-docs/**",
                                "/swagger-resources/**", "/webjars/**").permitAll()*/

                        .requestMatchers("/v1/auth/**").permitAll()
                        .requestMatchers("/v1/auth/getUserCounts").hasRole("ADMIN")
                        .anyRequest().authenticated());

        /**
         * handle authentication and authorization failures
         */
        http.exceptionHandling(ex -> ex
                .authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint(new ObjectMapper()))
                .accessDeniedHandler(new CustomAccessDeniedHandler(new ObjectMapper())));
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    public AuthenticationManager authenticationManager( UserDetailsService userDetailsService,
                PasswordEncoder passwordEncoder) {
            TrackerUsernamePwdAuthenticationProvider authenticationProvider =
                    new TrackerUsernamePwdAuthenticationProvider(userDetailsService, passwordEncoder);
            ProviderManager providerManager = new ProviderManager(authenticationProvider);
            providerManager.setEraseCredentialsAfterAuthentication(false);
        return  providerManager;
    }



}
