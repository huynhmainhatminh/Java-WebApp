//package com.test5.controllers;
//import com.test5.security.JwtUtils;
//import com.test5.model.requests.Login;
//import com.test5.model.requests.Register;
//import com.test5.pojo.User;
//import com.test5.services.IUsersServices;
//import com.test5.services.UsersServices;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//
//@RestController
//@RequestMapping("/api")
//public class UsersControllers {
//
//
//
//    @Autowired
//    private JwtUtils jwtUtils;
//
////    @PostMapping("/register")
////    public Register register(
////            @RequestParam(value = "username", defaultValue = "None") String username,
////            @RequestParam(value = "password", defaultValue = "None") String password,
////            @RequestParam(value = "full_name", defaultValue = "None") String full_name
////    ) {
////        IUsersServices usersService = new UsersServices();
////        User user = new User();
////
////        if (usersService.usernameExists(username)) {
////            return Register.builder()
////                    .success(false)
////                    .username(username)
////                    .message("Username Đã Tồn Tại")
////                    .build();
////        } else {
////            user.setUsername(username);
////            user.setPassword(password);
////            user.setFullName(full_name);
////            usersService.addUser(user);
////            return Register.builder()
////                    .success(true)
////                    .username(username)
////                    .message("Đăng Ký Thành Công")
////                    .build();
////        }
////    }
//
//
//
//    @PostMapping("/register")
//    public ResponseEntity<?> register(@ModelAttribute Register register) {
//
//        IUsersServices usersService = new UsersServices();
//        User user = new User();
//
//        if (usersService.usernameExists(register.getUsername())) {
//            return ResponseEntity.badRequest().body(
//                    new ApiRSP(false, "Tên tài khoản đã tồn tại!")
//            );
//        }
//
//        if (!register.getPassword().equals(register.getConfirmPassword())) {
//            return ResponseEntity.badRequest().body(
//                    new ApiRSP(false, "Mật khẩu xác nhận không khớp!")
//            );
//        }
//
//        // thêm user mới
//        user.setUsername(register.getUsername());
//        user.setPassword(register.getPassword());
//        user.setFullName(register.getFullName());
//        usersService.addUser(user);
//
//        return ResponseEntity.ok(
//                new ApiRSP(true, "Đăng ký thành công! Mời bạn đăng nhập để tiếp tục.")
//        );
//    }
//
////    @PostMapping("/login")
////    public ResponseEntity<?> login(@ModelAttribute Login login) {
////
////        IUsersServices usersService = new UsersServices();
////
////        if (usersService.checkLogin(login.getUsername(), login.getPassword())) {
////            String token = jwtUtils.generateToken(login.getUsername(), usersService.findUserByUsername(login.getUsername()).getRoleName());
////            return ResponseEntity.ok(
////                    new ApiRSP(true, "Đăng nhập thành công")
////            );
////        } else {
////            return ResponseEntity.badRequest().body(
////                    new ApiRSP(false, "Đăng nhập thất bại!")
////            );
////        }
////    }
//
//
//
////    @PostMapping("/login")
////    public Login login(@RequestParam(value = "username", defaultValue = "None") String username,
////                       @RequestParam(value = "password", defaultValue = "None") String password) {
////
////        IUsersServices usersService = new UsersServices();
////        if (usersService.checkLogin(username, password)) {
////            // JwtUtils jwtUtils = new JwtUtils();
////            String token = jwtUtils.generateToken(username, usersService.findUserByUsername(username).getRoleName());
////            return Login.builder()
////                    .success(true)
////                    .message("Đăng Nhập Thành Công")
////                    .token(token)
////                    .build();
////        } else {
////            return Login.builder()
////                    .success(false)
////                    .message("Đăng Nhập Thất Bại")
////                    .build();
////        }
////    }
//
//    @GetMapping("/id")
//    public User finduserbyid(@RequestParam(value = "id") int id) {
//        IUsersServices usersService = new UsersServices();
//        return usersService.findUserById(id);
//    }
//
//
////    @GetMapping("/username")
////    public User finduserbyusername(@RequestParam(value = "username") String username) {
////        IUsersServices usersService = new UsersServices();
////        return usersService.findUserByUsername(username).getRoleName();
////    }
//
//
////    @GetMapping("/decode")
////    public ResponseEntity<?> finduserbyusername(@RequestHeader("Authorization") String token) {
////        if (token == null || !token.startsWith("Bearer ")) {
////            return ResponseEntity.badRequest().body("Invalid Authorization header");
////        }
////        String token_jwt = token.replace("Bearer ", "");
////        if (jwtUtils.validateToken(token_jwt)) {
////            String username = jwtUtils.extractUsername(token_jwt);
////            String role = jwtUtils.extractRole(token_jwt);
////            return ResponseEntity.accepted().body(" "+username+" | "+role+" ");
////
////        }
////        return ResponseEntity.badRequest().body("Token Không Hợp Lệ");
////    }
//
//
//}
