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


    @PostMapping("/qr")
    @ResponseBody
    public ResponseEntity<?> handleForm(@RequestParam BigDecimal amount) {
        String url = "https://img.vietqr.io/image/ACB-22749061-compact1.jpg?addInfo=phucvu02891&amount="+amount+"";
        // model.addAttribute("qrUrl", url);
        return ResponseEntity.ok(url);
    }


    @GetMapping("/naptien")
    public String naptien(Model model) {
        BigDecimal money = userService.findByUsername("phucvu02891").getWalletBalance();
        model.addAttribute("username", "phucvu02891");
        model.addAttribute("balance_amount", money);
//        User user = new User();
//        user.setUsername("USER");
//        user.setWalletBalance(10000);
//        String qrUrl = "";
//        model.addAttribute("user", user);
        return "user/naptien";
    }


}
