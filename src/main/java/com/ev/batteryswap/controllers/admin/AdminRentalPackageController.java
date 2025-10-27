package com.ev.batteryswap.controllers.admin;

import com.ev.batteryswap.pojo.RentalPackage;
import com.ev.batteryswap.services.IAdminRentalPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/admin/packages")
public class AdminRentalPackageController {

    @Autowired
    private IAdminRentalPackageService packageService;

    @GetMapping
    public String listPackages(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<RentalPackage> packagePage = packageService.findAll(PageRequest.of(page, 15));
        model.addAttribute("packagePage", packagePage);
        model.addAttribute("stats", packageService.getStatistics());
        return "admin/rental_packages";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("package", new RentalPackage());
        return "admin/package_form";
    }

    @PostMapping
    public String savePackage(@ModelAttribute("package") RentalPackage rentalPackage, RedirectAttributes redirectAttributes) {
        packageService.save(rentalPackage);
        redirectAttributes.addFlashAttribute("successMessage", "Lưu gói thuê thành công!");
        return "redirect:/admin/packages";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<RentalPackage> rentalPackage = packageService.findById(id);
        if (rentalPackage.isPresent()) {
            model.addAttribute("package", rentalPackage.get());
            return "admin/package_form";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy gói thuê!");
        return "redirect:/admin/packages";
    }

    @GetMapping("/delete/{id}")
    public String deletePackage(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        packageService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa gói thuê thành công!");
        return "redirect:/admin/packages";
    }
}