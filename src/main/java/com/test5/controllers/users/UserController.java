package com.test5.controllers.users;
import com.test5.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {

    @Autowired
    private JwtUtils jwtUtils;


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/contact")
    public String contact(@CookieValue(value = "jwt", required = false) String token, Model model) {

        if (token == null || !jwtUtils.validateToken(token)) {
            return "login";
        }

        String username = jwtUtils.extractUsername(token);

        model.addAttribute("username", username);

        return "/user/contact";

    }


    @GetMapping("/my")
    public String my(@CookieValue(value = "jwt", required = false) String token, Model model) {
        if (token == null || !jwtUtils.validateToken(token)) {
            return "login";
        }

        String username = jwtUtils.extractUsername(token);

        model.addAttribute("username", username);

        return "/user/my";
    }

}
