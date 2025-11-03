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

    @PostMapping("/updateBalanceById")
    public String updateBalanceById(@RequestParam(value = "userId") int userId, @RequestParam(value = "name_pack") String name_pack,
                                        @RequestParam(value = "days") int days) {


        BigDecimal balance_user = userServices.findById(userId).getWalletBalance();

        if ( "Gói Cơ Bản 30 ngày".equals(name_pack) && days == 30 && balance_user.compareTo(new BigDecimal("270.000")) >= 0 ) {
            BigDecimal newBalance = balance_user.subtract(new BigDecimal("270.000"));
            userServices.updateBalanceById(userId, newBalance);

            return newBalance.toString();

        } else if ("Gói Nâng Cao 90 ngày".equals(name_pack) && days == 90 && balance_user.compareTo(new BigDecimal("810.000")) >= 0 ) {
            BigDecimal newBalance = balance_user.subtract(new BigDecimal("810.000"));
            userServices.updateBalanceById(userId, newBalance);
            return newBalance.toString();
        } else if ("Gói Cao Cấp 180 ngày".equals(name_pack) && days == 180 && balance_user.compareTo(new BigDecimal("1.620.000")) >= 0 ) {
            BigDecimal newBalance = balance_user.subtract(new BigDecimal("1.620.000"));
            userServices.updateBalanceById(userId, newBalance);
            return newBalance.toString();
        } else {
            return "Không đủ tiền";
        }

//        BigDecimal balance_user = userServices.findById(userId).getWalletBalance();
//
//        BigDecimal price = new BigDecimal("270.000");        // Gói dịch vụ 270.000
//
//        BigDecimal newBalance = balance_user.subtract(price);
//
//        userServices.updateBalanceById(userId, newBalance);
//        System.out.println(balance_user);
//        return newBalance;
    }


}
