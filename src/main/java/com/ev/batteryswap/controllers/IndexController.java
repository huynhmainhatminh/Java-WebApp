package com.ev.batteryswap.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/my")
    public String my() {
        return "user/my";
    }

    @GetMapping("/naptien")
    public String naptien() {
        return "user/naptien";
    }


}
