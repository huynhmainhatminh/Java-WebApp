package com.ev.batteryswap.controllers;
import com.ev.batteryswap.dto.APIResponse;
import com.ev.batteryswap.pojo.Battery;
import com.ev.batteryswap.pojo.RentalPackage;
import com.ev.batteryswap.pojo.Station;
import com.ev.batteryswap.pojo.User;
import com.ev.batteryswap.security.JwtUtils;
import com.ev.batteryswap.services.BatteryService;
import com.ev.batteryswap.services.StationService;
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

    @Autowired
    private StationService stationService;

    @Autowired
    private JwtUtils jwtUtils;


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


    // API đổi pin
    @PostMapping("/change")
    public ResponseEntity<?> doiPin(@RequestParam("station_id") Integer station_id, @RequestParam("pin_id") Integer pin_id,
                         @RequestParam("serial_number") String serial_number, @RequestParam("model_batteries") String model_batteries,
                         @RequestParam(value = "pin_id_user", required = false) Integer pin_id_user, @CookieValue(value = "jwt") String token) {

        Station station = stationService.getStationById(station_id);

        try {
            if (station != null) { // kiểm tra trạm có tồn tại trong database không

                Battery battery = batteryService.getBatteryById(pin_id);
                if  (battery != null && battery.getStation().equals(station) && battery.getModel().equals(model_batteries) && battery.getSerialNumber().equals(serial_number) && battery.getStatus().equals("AVAILABLE")) {


                    String username = jwtUtils.extractUsername(token); // trích xuất username từ token

                    BigDecimal balance_user = userService.findByUsername(username).getWalletBalance(); // lấy giá trị tiền hiện tại của người dùng

                    if (balance_user.compareTo(battery.getAmount()) >= 0) { // kiểm tra số dư có đủ tiền không
                        BigDecimal newBalance = balance_user.subtract(battery.getAmount()); // trừ tiền của người dùng
                        User user = userService.findByUsername(username);
                        batteryService.updateCurrentUser(user, pin_id);
                        batteryService.updateStatusById(pin_id, "RENTED"); // thay đổi trạng thái của Pin
                        userService.updateBalanceById(user.getId(), newBalance); // cập nhật lại số tiền
                        return ResponseEntity.ok("Đổi Pin Thành Công");
                        // return "Đổi Pin Thành Công";
                    } else {
                        return ResponseEntity.badRequest().body("Không Đủ Số Dư");
                        // return "Không Đủ Số Dư";
                    }

                }
            }
            return ResponseEntity.badRequest().body("Đổi Pin Thất Bại");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Đổi Pin Thất Bại");
        }
    }


}
