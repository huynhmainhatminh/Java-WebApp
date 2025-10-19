package com.test5.controllers;
import com.test5.model.requests.LoginRequests;
import com.test5.model.response.LoginResponse;
import com.test5.security.JwtUtils;
import com.test5.services.LoginServices;
import com.test5.services.UsersServices;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



/*

API đăng nhập áp dụng cho cả 3 role admin, staff, users

 */


@RestController
@RequestMapping("/api")
public class LoginController {

    // LoginServices loginServices = new LoginServices();

    @Autowired
    private LoginServices loginServices;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UsersServices usersService;


    @PostMapping("/login")
    public ResponseEntity<?> LoginAccount(@ModelAttribute LoginRequests login, HttpServletResponse response) {

        if (loginServices.checkLogin(login.getUsername(), login.getPassword())) {
            String token = jwtUtils.generateToken(login.getUsername(), usersService.findUserByUsername(login.getUsername()).getRoleName());

            // thiết lập cookie cho trang để chuyển hướng đến các trang khác
            ResponseCookie cookie = ResponseCookie.from("jwt", token)
                    .httpOnly(true)
                    .secure(false) // đặt true nếu dùng HTTPS
                    .path("/")
                    .maxAge(24 * 60 * 60)
                    .build();
            response.addHeader("Set-Cookie", cookie.toString());


            return ResponseEntity.ok(
                    new LoginResponse(true, "Đăng nhập thành công", token)
            );
        } else {
            return ResponseEntity.badRequest().body(
                    new LoginResponse(false, "Đăng nhập thất bại")
            );
        }
    }

}
