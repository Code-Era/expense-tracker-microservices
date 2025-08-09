package com.expense_category_service.jwtutil;


import com.expense_category_service.constant.AppConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JWTTokenValidatorFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {



        String jwt = request.getHeader(AppConstants.JWT_HEADER);
        if(null != jwt){
          try{
              Environment env = getEnvironment();
              if(env != null) {
                  String key = env.getProperty(AppConstants.JWT_SECRET_KEY,
                          AppConstants.JWT_SECRET_DEFAULT_VALUE);
                  SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));

                  if(secretKey != null){
                     // Jwts

                    Claims claims = Jwts.parser().verifyWith(secretKey).build()
                            .parseSignedClaims(jwt).getPayload();
                      System.out.println("Claims: " + claims);

                    if(claims != null){
                        String username = claims.get("username", String.class);
                        String authority = claims.get("authority", String.class);

                        System.out.println("Validating token for user: " + username);
                        System.out.println("Authorities from token: " + authority);
                        if(username != null && authority != null){
                            Authentication authentication = new UsernamePasswordAuthenticationToken(username,
                                    jwt, AuthorityUtils.commaSeparatedStringToAuthorityList(authority));
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    }else   System.out.println("No CLAIMS token found");
                  }else   System.out.println("No secretKey token found");


              }


          }catch (io.jsonwebtoken.ExpiredJwtException e) {
              sendUnauthorizedResponse(response, "Token expired. Please login again.");
              return;

          } catch (io.jsonwebtoken.JwtException e) {
              sendUnauthorizedResponse(response, "Invalid token");
              return;
          } catch (Exception e) {
              sendUnauthorizedResponse(response, "Token validation failed");
              return;
          }
        }else System.out.println("No JWT token found");

    filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();  // ya getRequestURI()
        return path.startsWith("/v1/auth/");
    }

    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        String json = String.format("""
    {
      "status": 401,
      "message": "%s"
    }
    """, message);

        response.getWriter().write(json);
    }

}
