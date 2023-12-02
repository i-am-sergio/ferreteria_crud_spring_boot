package com.tecsup.ferreteria.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// when it comes to security context, this class gives you a visibility to a user that is connected.
//you have his informations
@RestController
public class SecurityRestController {
    //1rst method
    @RequestMapping("/profile")
    public Authentication authentication(Authentication authentication){
        return authentication;
    }

    //2nd method
    @RequestMapping("/profile2")
    public Authentication authentication2(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
