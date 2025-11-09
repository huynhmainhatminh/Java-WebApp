package com.ev.batteryswap.controllers;
import com.ev.batteryswap.pojo.Station;
import com.ev.batteryswap.services.UserService;
import com.ev.batteryswap.services.interfaces.IBatteryService;
import com.ev.batteryswap.services.interfaces.IStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    UserService userService;

    @Autowired
    private IStationService stationService;

    @Autowired
    private IBatteryService batteryService;

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


    @GetMapping("/profile")
    public String profile(Model model) {
        model.addAttribute("username", "nhatnam3332");
        return "user/profile";
    }

    @GetMapping("/contact")
    public String contact() {
        return "user/contact";
    }

//    @GetMapping("/dashboard")
//    public String dashboard() {
//        return "user/dashboard";
//    }


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
        model.addAttribute("money_amount", formattedMoney);


        model.addAttribute("username", "nhatnam3332");
        model.addAttribute("balance_amount", formattedMoney);

        return "user/naptien";
    }


    @GetMapping("/dashboard")
    public String listStations(Model model, @RequestParam(defaultValue = "0") int page) {
        BigDecimal money = userService.findByUsername("nhatnam3332").getWalletBalance();
        String formattedMoney = String.format("%,.0f VND", money);

        model.addAttribute("username", "nhatnam3332");
        model.addAttribute("money_amount", formattedMoney);

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
