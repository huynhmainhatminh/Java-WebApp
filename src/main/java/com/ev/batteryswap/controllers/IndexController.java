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
        model.addAttribute("username", "nhatnam3332");
        return "user/my";
    }

    @GetMapping("/contact")
    public String contact() {
        return "user/contact";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "user/dashboard";
    }


    @PostMapping("/qr")
    @ResponseBody
    public ResponseEntity<?> handleForm(@RequestParam BigDecimal amount) {
        String url = "https://img.vietqr.io/image/ACB-22749061-compact1.jpg?addInfo=nhatnam3332&amount="+amount;
        // model.addAttribute("qrUrl", url);
        return ResponseEntity.ok(url);
    }


    @GetMapping("/naptien")
    public String naptien(Model model) {
        BigDecimal money = userService.findByUsername("nhatnam3332").getWalletBalance();

        // Định dạng tiền tệ theo locale Việt Nam
        String formattedMoney = String.format("%,.0f VND", money);

        model.addAttribute("username", "nhatnam3332");
        model.addAttribute("balance_amount", formattedMoney);

        return "user/naptien";
    }


}
