package com.ev.batteryswap.controllers;
import com.ev.batteryswap.pojo.Station;
import com.ev.batteryswap.security.JwtUtils;
import com.ev.batteryswap.services.UserService;
import com.ev.batteryswap.services.interfaces.IBatteryService;
import com.ev.batteryswap.services.interfaces.IStationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import jakarta.servlet.http.Cookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private IStationService stationService;

    @Autowired
    private IBatteryService batteryService;

    @Autowired
    private JwtUtils  jwtUtils;

    @GetMapping("/")
    public String index(@CookieValue(value = "jwt", required = false) String token) {

        if (token == null || !jwtUtils.validateToken(token)) {
            return "index";
        } else {
            return "redirect:/my";
        }
    }

    public void update_info(Model model, String token) {

        String username = jwtUtils.extractUsername(token); // lấy username từ token jwt
        String role = jwtUtils.extractRole(token); // lấy role người dùng từ token jwt

        model.addAttribute("username", username);
        model.addAttribute("role", role);
        BigDecimal money = userService.findByUsername(username).getWalletBalance();

        // Định dạng tiền tệ theo locale Việt Nam
        String formattedMoney = String.format("%,.0f VND", money);
        model.addAttribute("money_amount", formattedMoney);

        model.addAttribute("username", username);
        model.addAttribute("balance_amount", formattedMoney);
    }

    @GetMapping("/login")
    public String login(@CookieValue(value = "jwt", required = false) String token) {
        if (token == null || !jwtUtils.validateToken(token)) {
            return "login";
        } else {
            return "redirect:/my";
        }
    }

    @GetMapping("/register")
    public String register(@CookieValue(value = "jwt", required = false) String token) {
        if (token == null || !jwtUtils.validateToken(token)) {
            return "register";
        } else {
            return "redirect:/my";
        }
    }

    @GetMapping("/my")
    public String my(@CookieValue(value = "jwt", required = false) String token, Model model) {
        if (token == null || !jwtUtils.validateToken(token)) {
            return "login";
        } else {
            update_info(model, token);
            return "user/my";
        }
    }


    @GetMapping("/profile")
    public String profile(@CookieValue(value = "jwt", required = false) String token, Model model) {


        if (token == null || !jwtUtils.validateToken(token)) {
            return "login";
        } else {
            update_info(model, token);
            return "user/profile";
        }

    }

    @GetMapping("/contact")
    public String contact(@CookieValue(value = "jwt", required = false) String token, Model model) {

        if (token == null || !jwtUtils.validateToken(token)) {
            return "login";
        } else {
            update_info(model, token);
            return "user/contact";
        }

    }

    @GetMapping("/lichsu")
    public String lichsu(@CookieValue(value = "jwt", required = false) String token, Model model) {

        if (token == null || !jwtUtils.validateToken(token)) {
            return "login";
        } else {
            update_info(model, token);
            return "user/lichsu";
        }
    }

    @GetMapping("/packages")
    public String packages() {
        return "packages";
    }


    // tạo mã QR ACB
    @PostMapping("/qr")
    @ResponseBody
    public ResponseEntity<?> handleForm(@RequestParam BigDecimal amount, @CookieValue(value = "jwt", required = false) String token) {
        String username = jwtUtils.extractUsername(token); // lấy username từ token jwt
        String url = "https://img.vietqr.io/image/ACB-22749061-compact1.jpg?addInfo="+username+"&amount="+amount;
        return ResponseEntity.ok(url);
    }


    @GetMapping("/naptien")
    public String naptien(@CookieValue(value = "jwt", required = false) String token, Model model) {

        if (token == null || !jwtUtils.validateToken(token)) {
            return "login";
        } else {
            update_info(model, token);
            return "user/naptien";
        }
    }


    @GetMapping("/dashboard")
    public String listStations(Model model, @RequestParam(defaultValue = "0") int page, @CookieValue(value = "jwt", required = false) String token) {
        update_info(model, token);
        Page<Station> stationPage = stationService.filterStations(null, PageRequest.of(page, 3));
//        model.addAttribute("stations", stationPage.getContent());
//        return "user/dashboard"; // chính là file HTML bạn gửi ở trên

        // List<Station> stations =  batteryService.getAllStations();

        // Map<StationId, Map<key,value>> để lưu thống kê pin từng trạm
        Map<Integer, Map<String, Long>> batteryStatsByStation = new HashMap<>();

        for (Station station : stationPage) {
            Map<String, Long> stats = batteryService.getBatteryStatisticsForStation(station);
            batteryStatsByStation.put(station.getId(), stats);
        }

        model.addAttribute("stations", stationPage);
        model.addAttribute("batteryStatsByStation", batteryStatsByStation);
        return "user/dashboard";
    }

}
