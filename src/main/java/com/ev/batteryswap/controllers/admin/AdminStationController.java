package com.ev.batteryswap.controllers.admin;

import com.ev.batteryswap.pojo.Battery;
import com.ev.batteryswap.pojo.Station;
import com.ev.batteryswap.services.IAdminBatteryService;
import com.ev.batteryswap.services.IAdminStationService;
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
    private IAdminStationService adminStationService;

    @Autowired
    private IAdminBatteryService adminBatteryService;

    @GetMapping
    public String listStations(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "15") int size,
                               @RequestParam(required = false) String search) {
        Page<Station> stationPage = adminStationService.filterStations(search, PageRequest.of(page, size));
        model.addAttribute("stationPage", stationPage);
        model.addAttribute("stats", adminStationService.getStationStatistics());
        model.addAttribute("search", search);
        return "admin/stations";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("station", new Station());
        model.addAttribute("pageTitle", "Thêm Trạm Mới");
        return "admin/station_form";
    }

    // Đổi tên phương thức `createStation` thành `saveStation`
    @PostMapping
    public String saveStation(@ModelAttribute("station") Station station, RedirectAttributes redirectAttributes) {
        try {
            adminStationService.saveStation(station);
            redirectAttributes.addFlashAttribute("successMessage", "Lưu thông tin trạm thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi lưu trạm: " + e.getMessage());
        }
        return "redirect:/admin/stations";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Station> station = adminStationService.getStationById(id);
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
            adminStationService.deleteStation(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa trạm thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa trạm: " + e.getMessage());
        }
        return "redirect:/admin/stations";
    }

    @GetMapping("/{id}")
    public String viewStationDetails(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Station> stationOptional = adminStationService.getStationById(id);
        if (stationOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy trạm!");
            return "redirect:/admin/stations";
        }

        Station station = stationOptional.get();
        Page<Battery> batteriesInStation = adminBatteryService.filterBatteries(id, null, null, PageRequest.of(0, Integer.MAX_VALUE));

        model.addAttribute("station", station);
        model.addAttribute("batteries", batteriesInStation.getContent());
        model.addAttribute("batteryStats", adminBatteryService.getBatteryStatisticsForStation(station));

        return "admin/station_detail";
    }
}