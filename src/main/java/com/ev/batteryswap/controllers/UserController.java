package com.ev.batteryswap.controllers;


import com.ev.batteryswap.pojo.User;
import com.ev.batteryswap.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserServices userServices;

    @PostMapping("/information")
    public User information(@RequestParam(value = "username") String username) {
        return userServices.findByUsername(username);
    }

    @GetMapping("/information/{id}")
    public User getInformationById(@PathVariable("id") int id) {
         return userServices.findById(id);
    }
}
