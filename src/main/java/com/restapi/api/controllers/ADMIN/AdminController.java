package com.restapi.api.controllers.ADMIN;
import com.restapi.api.services.UsersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")

public class AdminController {

    @Autowired
    UsersServices usersServices;


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("users", usersServices.getAllUsers());
        return "index"; // trỏ đến file templates/index.html
    }


    @GetMapping("/delete/{username}")
    public String deleteUser(@PathVariable("username") String username) {
        usersServices.deleteByUsername(username);
        return "redirect:/admin/"; // ✅ sửa lại: thêm /admin/
    }

}
