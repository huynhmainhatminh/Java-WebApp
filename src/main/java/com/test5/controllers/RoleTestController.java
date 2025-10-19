package com.test5.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api") // prefix cho tất cả route trong controller

public class RoleTestController {

    @GetMapping("/member/hello")
    public String memberArea() {
        return "Xin chào MEMBER! Bạn đã vào khu vực dành cho member.";
    }

    @GetMapping("/staff/hello")
    public String staffArea() {
        return "Xin chào STAFF! Bạn đã vào khu vực dành cho staff.";
    }

    @GetMapping("/admin/hello")
    public String adminArea() {
        return "Xin chào ADMIN! Bạn đã vào khu vực quản trị.";
    }

}
