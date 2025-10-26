package com.restapi.api.controllers;
import com.restapi.api.model.requests.RegisterRequests;
import com.restapi.api.pojo.User;
import com.restapi.api.services.RegisterServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")

public class RegisterController {

    @Autowired
    RegisterServices registerServices;


    @PostMapping("/register")
    public String register(@ModelAttribute RegisterRequests registerRequests) {


        if (registerServices.existsByUsername(registerRequests.getUsername())) {
            return "Tên tài khoản đã tồn tại";
        }

        if (!registerRequests.getPassword().equals(registerRequests.getConfirmPassword())) {
            return "Mật khẩu xác nhận không khớp!";
        }

        // RegisterServices registerServices = new RegisterServices();  cái này lỗi

        User user = new User();

        user.setFullName(registerRequests.getFullName());
        user.setUsername(registerRequests.getUsername());
        user.setPassword(registerRequests.getPassword());

        registerServices.addUser(user);
        return "Đăng ký thành công";
    }

}
