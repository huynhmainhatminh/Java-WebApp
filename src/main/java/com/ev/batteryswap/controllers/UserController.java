package com.ev.batteryswap.controllers;
import com.ev.batteryswap.dto.APIResponse;
import com.ev.batteryswap.pojo.RentalPackage;
import com.ev.batteryswap.pojo.User;
import com.ev.batteryswap.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/information")
    public User information(@RequestParam(value = "username") String username) {
        return userService.findByUsername(username);
    }

    @GetMapping("/information/{id}")
    public User getInformationById(@PathVariable("id") int id) {
         return userService.findById(id);
    }

    @PostMapping("/rentalPackage")
    public ResponseEntity<?> updateBalanceById(@RequestParam(value = "userId") int userId, @RequestParam(value = "name_pack") String name_pack,
                                            @RequestParam(value = "days") int days) {

        User user = new User();
        RentalPackage rentalPackage = new RentalPackage();


        BigDecimal balance_user = userService.findById(userId).getWalletBalance();

        if ( "Gói Cơ Bản 30 ngày".equals(name_pack) && days == 30 && balance_user.compareTo(new BigDecimal("270.000")) >= 0 ) {
            BigDecimal newBalance = balance_user.subtract(new BigDecimal("270.000"));
            userService.updateBalanceById(userId, newBalance);
            user.setId(userId);
            rentalPackage.setUser(user);
            rentalPackage.setName(name_pack);
            rentalPackage.setPrice(new BigDecimal("270.000"));
            rentalPackage.setDurationDays(days);
            userService.registerPackage(rentalPackage);
            return ResponseEntity.ok(
                    new APIResponse(true, "Đăng Ký Gói Thành Công.")
            );

        } else if ("Gói Nâng Cao 90 ngày".equals(name_pack) && days == 90 && balance_user.compareTo(new BigDecimal("810.000")) >= 0 ) {
            BigDecimal newBalance = balance_user.subtract(new BigDecimal("810.000"));
            userService.updateBalanceById(userId, newBalance);
            user.setId(userId);
            rentalPackage.setUser(user);
            rentalPackage.setName(name_pack);
            rentalPackage.setPrice(new BigDecimal("810.000"));
            rentalPackage.setDurationDays(days);
            userService.registerPackage(rentalPackage);
            return ResponseEntity.ok(
                    new APIResponse(true, "Đăng Ký Gói Thành Công.")
            );

        } else if ("Gói Cao Cấp 180 ngày".equals(name_pack) && days == 180 && balance_user.compareTo(new BigDecimal("1.620.000")) >= 0 ) {
            BigDecimal newBalance = balance_user.subtract(new BigDecimal("1.620.000"));
            userService.updateBalanceById(userId, newBalance);
            user.setId(userId);
            rentalPackage.setUser(user);
            rentalPackage.setName(name_pack);
            rentalPackage.setPrice(new BigDecimal("1.620.000"));
            rentalPackage.setDurationDays(days);
            return ResponseEntity.ok(
                    new APIResponse(true, "Đăng Ký Gói Thành Công.")
            );
        } else {
            return ResponseEntity.badRequest().body(
                    new APIResponse(false, "Đăng Ký Gói Thất Bại.")
            );
        }




//


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
