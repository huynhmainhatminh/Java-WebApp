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

    @GetMapping("/profile")
    public String profile() {
        return "/user/profile";
    }

    @GetMapping("/history")
    public String history() {
        return "/user/history";
    }

    @GetMapping("/packages")
    public String packages() {
        return "/packages";
    }


    @GetMapping("/schedule")
    public String schedule() {
        return "/user/schedule";
    }

    @GetMapping("/register")
    public String register(@CookieValue(value = "jwt", required = false) String token) {
        if (token == null || !jwtUtils.validateToken(token)) {
            return "register";

        } else {
            return "redirect:/my";
        }
    }

    @GetMapping("/login")
    public String login(@CookieValue(value = "jwt", required = false) String token) {
        if (token == null || !jwtUtils.validateToken(token)) {
            return "login";
        } else {
            return "redirect:/my";
        }
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
