package com.ev.batteryswap.controllers;
import com.ev.batteryswap.dto.AuthRequestLogin;
import com.ev.batteryswap.dto.AuthRequestRegister;
import com.ev.batteryswap.pojo.User;
import com.ev.batteryswap.services.AuthServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    AuthServices authServices;

    @PostMapping("/login")
    public boolean login(@RequestBody AuthRequestLogin authRequest) {
        return authServices.login(authRequest.getUsername(), authRequest.getPassword());
    }


    @PostMapping("/register")
    public User register(@RequestBody AuthRequestRegister authRequest) {
        if (!authRequest.getPassword().equals(authRequest.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        if (authServices.existsByUsername(authRequest.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        User user = new User();
        user.setFullName(authRequest.getFullName());
        user.setUsername(authRequest.getUsername());
        user.setPassword(authRequest.getPassword());
        return authServices.register(user);
    }

}
