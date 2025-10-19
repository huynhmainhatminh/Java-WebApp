package com.test5.controllers;
import com.test5.model.requests.RegisterRequests;
import com.test5.model.response.LoginResponse;
import com.test5.model.response.RegisterResponse;
import com.test5.pojo.User;
import com.test5.services.RegisterServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RegisterController {

    @Autowired
    private RegisterServices registerServices;


    @PostMapping("/register")
    public ResponseEntity<?> register(@ModelAttribute RegisterRequests register) {

        User user = new User();

        if (registerServices.usernameExists(register.getUsername())) {
            return ResponseEntity.badRequest().body(
                    new RegisterResponse(false, "Tên tài khoản đã tồn tại!")
            );
        }

        if (!register.getPassword().equals(register.getConfirmPassword())) {
            return ResponseEntity.badRequest().body(
                    new RegisterResponse(false, "Mật khẩu xác nhận không khớp!")
            );
        }

        // thêm user mới
        user.setUsername(register.getUsername());
        user.setPassword(register.getPassword());
        user.setFullName(register.getFullName());
        registerServices.addUser(user);

        return ResponseEntity.ok(
                new RegisterResponse(true, "Đăng ký thành công! Mời bạn đăng nhập để tiếp tục.")
        );
    }

}
