package com.ev.batteryswap.controllers.admin;

import com.ev.batteryswap.pojo.Battery;
import com.ev.batteryswap.pojo.Station;
import com.ev.batteryswap.services.IBatteryService; // <-- Đã đổi tên
import com.ev.batteryswap.services.IStationService; // <-- Đã đổi tên
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/admin/stations")
public class AdminStationController {

    @Autowired
    private IStationService stationService; // <-- Đã đổi tên

    @Autowired
    private IBatteryService batteryService; // <-- Đã đổi tên

    @GetMapping
    public String listStations(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "15") int size,
                               @RequestParam(required = false) String search) {
        Page<Station> stationPage = stationService.filterStations(search, PageRequest.of(page, size)); // <-- Đã đổi tên
        model.addAttribute("stationPage", stationPage);
        model.addAttribute("stats", stationService.getStationStatistics()); // <-- Đã đổi tên
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
    public String saveStation(@ModelAttribute("station") Station station, RedirectAttributes redirectAttributes) {
        try {
            stationService.saveStation(station); // <-- Đã đổi tên
            redirectAttributes.addFlashAttribute("successMessage", "Lưu thông tin trạm thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi lưu trạm: " + e.getMessage());
        }
        return "redirect:/admin/stations";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Station> station = stationService.getStationById(id); // <-- Đã đổi tên
        if (station.isPresent()) {
            model.addAttribute("station", station.get());
            model.addAttribute("pageTitle", "Chỉnh Sửa Trạm");
            return "admin/station_form";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy trạm!");
        return "redirect:/admin/stations";
    }

    @GetMapping("/delete/{id}")
    public String deleteStation(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            stationService.deleteStation(id); // <-- Đã đổi tên
            redirectAttributes.addFlashAttribute("successMessage", "Xóa trạm thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa trạm: " + e.getMessage());
        }
        return "redirect:/admin/stations";
    }

    @GetMapping("/{id}")
    public String viewStationDetails(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Station> stationOptional = stationService.getStationById(id); // <-- Đã đổi tên
        if (stationOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy trạm!");
            return "redirect:/admin/stations";
        }

        Station station = stationOptional.get();
        Page<Battery> batteriesInStation = batteryService.filterBatteries(id, null, null, PageRequest.of(0, Integer.MAX_VALUE)); // <-- Đã đổi tên

        model.addAttribute("station", station);
        model.addAttribute("batteries", batteriesInStation.getContent());
        model.addAttribute("batteryStats", batteryService.getBatteryStatisticsForStation(station)); // <-- Đã đổi tên

        return "admin/station_detail";
    }
}