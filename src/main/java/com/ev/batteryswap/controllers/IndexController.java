package com.ev.batteryswap.controllers;
import com.ev.batteryswap.pojo.User;
import com.ev.batteryswap.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
public class IndexController {

    @Autowired
    UserService userService;

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
    public String my(Model model) {
        model.addAttribute("username", "phucvu02891");
        return "user/my";
    }

    @GetMapping("/contact")
    public String contact() {
        return "user/contact";
    }


}
