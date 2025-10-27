package com.ev.batteryswap.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    /**
     * Hiển thị trang dashboard chính của admin.
     * Hiện tại, dashboard chính là trang quản lý pin, nên sẽ redirect về đó.
     */
    @GetMapping("/dashboard")
    public String showDashboard() {
        return "redirect:/admin/batteries";
    }
//    @GetMapping("/users")
//    public String showUsers() {
//        return "redirect:/admin/users";
//    }

}