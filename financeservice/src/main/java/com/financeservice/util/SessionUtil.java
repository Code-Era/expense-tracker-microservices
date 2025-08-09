package com.financeservice.util;


import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SessionUtil {

  /*  @Autowired
    private UserRepository userRepository;
*/

    public String getCurrentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
    public boolean isCurrentUserAdmin() {
       String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

        return role.contains("ROLE_ADMIN") ? true : false;
    }

    /*public User getCurrentUserOrThrow() {
        String email = getCurrentUserEmail();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> ExceptionUtil.throwResourceNotFound("User", email));
    }*/

}
