package com.test5.controllers.admin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/login")
    public String adminArea() {
        return "/admin/login";
    }

    @GetMapping("/manage-member")
    public String manage_member() {
        return "/admin/manage-member";
    }


    @GetMapping("/manage-staff")
    public String manage_staff() {
        return "/admin/manage-staff";
    }

    @GetMapping("/manage-admin")
    public String manage_admin() {
        return "/admin/manage-admin";
    }

    @GetMapping("/dashboard")
    public String myArea() {
        return "/admin/dashboard";
    }

}
