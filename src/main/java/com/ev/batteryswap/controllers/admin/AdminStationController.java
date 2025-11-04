package com.ev.batteryswap.controllers.admin;

import com.ev.batteryswap.pojo.Battery;
import com.ev.batteryswap.pojo.Station;
import com.ev.batteryswap.services.interfaces.IBatteryService;
import com.ev.batteryswap.services.interfaces.IStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/admin/stations")
public class AdminStationController {

    @Autowired
    private IStationService stationService;

    @Autowired
    private IBatteryService batteryService;

    @GetMapping
    public String listStations(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "15") int size,
                               @RequestParam(required = false) String search) {
        Page<Station> stationPage = stationService.filterStations(search, PageRequest.of(page, size));
        model.addAttribute("stationPage", stationPage);
        model.addAttribute("stats", stationService.getStationStatistics());
        model.addAttribute("search", search);
        return "admin/stations";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("station", new Station());
        model.addAttribute("pageTitle", "Thêm Trạm Mới");
        return "admin/station_form";
    }

    @PostMapping
    public String saveStation(@ModelAttribute("station") Station stationFormData, RedirectAttributes redirectAttributes) {
        try {
            String successMessage;

            if (stationFormData.getId() == null) {
                // 1. LOGIC TẠO MỚI (ID là null)
                stationService.saveStation(stationFormData);
                successMessage = "Tạo trạm mới thành công!";
            } else {
                // 2. LOGIC CẬP NHẬT (ID đã tồn tại)
                Station existingStation = stationService.getStationById(stationFormData.getId());
                if (existingStation == null) {
                    redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: Không tìm thấy trạm để cập nhật!");
                    return "redirect:/admin/stations";
                }

                // Cập nhật các trường từ form
                existingStation.setName(stationFormData.getName());
                existingStation.setAddress(stationFormData.getAddress());
                existingStation.setQuan(stationFormData.getQuan());
                existingStation.setProvince(stationFormData.getProvince());
                existingStation.setStatus(stationFormData.getStatus());

                // Lưu lại đối tượng đã cập nhật
                stationService.saveStation(existingStation);
                successMessage = "Cập nhật thông tin trạm thành công!";
            }

            redirectAttributes.addFlashAttribute("successMessage", successMessage);

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi lưu trạm: " + e.getMessage());
        }
        return "redirect:/admin/stations";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Station station = stationService.getStationById(id);
        if (station != null) {
            model.addAttribute("station", station);
            model.addAttribute("pageTitle", "Chỉnh Sửa Trạm");
            return "admin/station_form";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy trạm!");
        return "redirect:/admin/stations";
    }

    @GetMapping("/delete/{id}")
    public String deleteStation(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            stationService.deleteStation(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa trạm thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa trạm: " + e.getMessage());
        }
        return "redirect:/admin/stations";
    }

    @GetMapping("/{id}")
    public String viewStationDetails(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Station station = stationService.getStationById(id);
        if (station == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy trạm!");
            return "redirect:/admin/stations";
        }

        Page<Battery> batteriesInStation = batteryService.filterBatteries(id, null, null, PageRequest.of(0, Integer.MAX_VALUE));

        model.addAttribute("station", station);
        model.addAttribute("batteries", batteriesInStation.getContent());
        model.addAttribute("batteryStats", batteryService.getBatteryStatisticsForStation(station));

        return "admin/station_detail";
    }
}