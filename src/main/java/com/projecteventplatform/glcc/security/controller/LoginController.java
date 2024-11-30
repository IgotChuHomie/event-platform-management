package com.projecteventplatform.glcc.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login"; // Points to login.html
    }

    @GetMapping("/")
    public String home(){
        return "redirect:/events" ;
    }
}