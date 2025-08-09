package com.expense_category_service.util;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    /*public User getCurrentUserOrThrow() {
        String email = getCurrentUserEmail();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> ExceptionUtil.throwResourceNotFound("User", email));
    }*/

}
