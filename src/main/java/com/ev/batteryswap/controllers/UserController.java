package com.ev.batteryswap.controllers;
import com.ev.batteryswap.dto.APIResponse;
import com.ev.batteryswap.pojo.RentalPackage;
import com.ev.batteryswap.pojo.Station;
import com.ev.batteryswap.pojo.User;
import com.ev.batteryswap.services.BatteryService;
import com.ev.batteryswap.services.UserService;
import com.ev.batteryswap.services.interfaces.IStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BatteryService batteryService;


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
    }


    @PostMapping("/doiPin")
    public String existsByIdAndStatusRented(@RequestParam("battery_out_id") Integer battery_out_id,
                                            @RequestParam("battery_in_id") Integer battery_in_id) {

        // battery_out_id là PIN của trạm còn trống
        // battery_in_id là PIN của người dùng đang cho thuê

        // kiểm tra xem PIN cho thuê của người dùng có nằm trong database không và kiểm tra xem PIN người dùng chọn hiện có trống không
        // thỏa mãn điều kiện khi : battery_out_id còn trống và battery_in_id đang cho thuê thì sẽ tiến hành
        // battery_in_id đang vào tình trạng sạc, còn battery_out_id đưa vào tình trạng cho thuê

        if (batteryService.existsByIdAndStatusRented(battery_in_id) && batteryService.existsByIdAndStatusEmpty(battery_out_id)) {
            batteryService.updateStatusById(battery_in_id, "CHARGING");
            batteryService.updateStatusById(battery_in_id, "RENTED");
            return "Hợp Lý";
        } else {
            return "Không Hợp Lý";
        }
    }



}
