package com.authuserservice.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/v1/api")
public class Test {


    @GetMapping("/test")
    public String test(Authentication  authentication) {

        System.out.println("Auth===> "+authentication.getAuthorities());

          return "Server is up and running";
    }

    @GetMapping("/profile")
    public String profile(Authentication  authentication) {


        return "Welcome profile";
    }
    @GetMapping("/expenses")
    public String cards(Authentication  authentication) {



        return "Welcome to expenses";
    }

    @GetMapping("/admin")
    public String admin(Authentication  authentication) {



        return "Welcome to admin";
    }

}
