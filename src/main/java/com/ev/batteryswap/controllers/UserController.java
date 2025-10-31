package com.ev.batteryswap.controllers;
import com.ev.batteryswap.pojo.RentalPackage;
import com.ev.batteryswap.pojo.User;
import com.ev.batteryswap.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


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

    @PostMapping("/rentalPackage")
    public RentalPackage rentalPackage(@RequestParam(value = "userId") int userId, @RequestParam(value = "name") String name,
                                       @RequestParam(value = "price") BigDecimal price, @RequestParam(value = "days") int days){
        RentalPackage rentalPackage = new RentalPackage();
        User user = new User();
        user.setId(userId);
        rentalPackage.setUser(user);
        rentalPackage.setName(name);
        rentalPackage.setPrice(price);
        rentalPackage.setDurationDays(days);
        return userServices.registerPackage(rentalPackage);
    }


}
