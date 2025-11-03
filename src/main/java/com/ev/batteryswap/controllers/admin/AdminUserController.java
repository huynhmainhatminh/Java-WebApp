package com.ev.batteryswap.controllers.admin;

import com.ev.batteryswap.pojo.User;
import com.ev.batteryswap.services.IUserService; // <-- Đã đổi tên
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired
    private IUserService userService; // <-- Đã đổi tên

    @GetMapping
    public String listUsers(Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "15") int size,
                            @RequestParam(required = false) String search) {
        Page<User> userPage = userService.filterUsers(search, PageRequest.of(page, size)); // <-- Đã đổi tên
        model.addAttribute("userPage", userPage);
        model.addAttribute("search", search);
        return "admin/users";
    }

    @GetMapping("/new")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/users_form";
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        try {
            userService.saveUser(user); // <-- Đã đổi tên
            redirectAttributes.addFlashAttribute("successMessage", "Thêm người dùng mới thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi thêm người dùng: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/update-role")
    public String updateUserRole(@RequestParam("id") Integer userId,
                                 @RequestParam("role") String role,
                                 RedirectAttributes redirectAttributes) {
        try {
            userService.updateUserRole(userId, role); // <-- Đã đổi tên
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật vai trò người dùng thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer userId, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(userId); // <-- Đã đổi tên
            redirectAttributes.addFlashAttribute("successMessage", "Xóa người dùng thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa người dùng: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }
}