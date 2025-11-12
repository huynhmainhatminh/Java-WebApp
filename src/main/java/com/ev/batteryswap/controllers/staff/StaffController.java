package com.ev.batteryswap.controllers.staff;

import com.ev.batteryswap.pojo.Battery;
import com.ev.batteryswap.pojo.SwapTransaction;
import com.ev.batteryswap.pojo.User;
import com.ev.batteryswap.services.interfaces.IUserService;
import com.ev.batteryswap.repositories.UserRepository;
import com.ev.batteryswap.services.interfaces.IBatteryService;
import com.ev.batteryswap.services.interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Collections;

@Controller
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private IBatteryService batteryService;

    @Autowired
    private ITransactionService transactionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IUserService userService;

    private User getCurrentStaffUser() {
        // Giả lập nhân viên có ID=2.
        // Trong tương lai, bạn có thể thay đổi ID này để test
        return userRepository.findById(2);
    }

    // Kiểm tra và chuyển hướng nếu nhân viên chưa được gán trạm
    private String checkStaffStation(User staff, RedirectAttributes redirectAttributes) {
        if (staff == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy tài khoản nhân viên. Vui lòng liên hệ Admin.");
            return "redirect:/admin/users"; // Chuyển hướng đến trang user
        }
        if (staff.getStation() == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tài khoản nhân viên chưa được gán trạm. Vui lòng liên hệ Admin.");
            return "redirect:/admin/users"; // Chuyển hướng đến trang user
        }
        return null; // Không có lỗi
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, RedirectAttributes redirectAttributes) {
        User staff = getCurrentStaffUser();
        String redirect = checkStaffStation(staff, redirectAttributes);
        if (redirect != null) return redirect;

        model.addAttribute("station", staff.getStation());
        model.addAttribute("stats", batteryService.getBatteryStatisticsForStation(staff.getStation()));
        return "staff/dashboard";
    }

    @GetMapping("/batteries")
    public String listBatteries(Model model,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(required = false) String status,
                                @RequestParam(required = false) String search,
                                RedirectAttributes redirectAttributes) { // Thêm redirectAttributes
        User staff = getCurrentStaffUser();
        String redirect = checkStaffStation(staff, redirectAttributes);
        if (redirect != null) return redirect;

        Integer stationId = staff.getStation().getId();
        Page<Battery> batteryPage = batteryService.filterBatteries(stationId, status, search, PageRequest.of(page, 15));

        model.addAttribute("batteryPage", batteryPage);
        model.addAttribute("station", staff.getStation());
        model.addAttribute("selectedStatus", status);
        model.addAttribute("search", search);
        return "staff/battery_inventory";
    }

    @GetMapping("/transactions")
    public String listTransactions(Model model,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(required = false) String paymentStatus,
                                   @RequestParam(required = false) String search,
                                   RedirectAttributes redirectAttributes) { // Thêm redirectAttributes
        User staff = getCurrentStaffUser();
        String redirect = checkStaffStation(staff, redirectAttributes);
        if (redirect != null) return redirect;

        Integer stationId = staff.getStation().getId();
        Page<SwapTransaction> transactionPage = transactionService.filterTransactions(stationId, paymentStatus, search, PageRequest.of(page, 15));

        model.addAttribute("transactionPage", transactionPage);
        model.addAttribute("station", staff.getStation());
        model.addAttribute("selectedPaymentStatus", paymentStatus);
        model.addAttribute("search", search);

        return "staff/transactions";
    }

    @GetMapping("/transactions/new")
    public String showCreateTransactionForm(Model model, RedirectAttributes redirectAttributes) { // Thêm redirectAttributes
        User staff = getCurrentStaffUser();
        String redirect = checkStaffStation(staff, redirectAttributes);
        if (redirect != null) return redirect;

        SwapTransaction transaction = new SwapTransaction();
        transaction.setStation(staff.getStation());

        model.addAttribute("transaction", transaction);

        // 1. Pin cũ (khách trả vào): Lấy TẤT CẢ pin đang RENTED
        Page<Battery> rentedBatteries = batteryService.filterBatteries(null, "RENTED", null, PageRequest.of(0, 1000));
        model.addAttribute("rentedBatteries", rentedBatteries.getContent());

        // 2. Pin mới (đưa cho khách): Lấy pin AVAILABLE tại trạm của staff
        Integer stationId = staff.getStation().getId();
        Page<Battery> availableBatteries = batteryService.filterBatteries(stationId, "AVAILABLE", null, PageRequest.of(0, 1000));
        model.addAttribute("availableBatteries", availableBatteries.getContent());

        try {
            // Lấy user có role driver
            List<User> drivers = userService.getUsersByRole("DRIVER");
            model.addAttribute("users", drivers);
        } catch (Exception e) {
            model.addAttribute("users", Collections.emptyList());
        }

        return "staff/transaction_form";
    }

    @PostMapping("/transactions")
    public String createTransaction(@ModelAttribute("transaction") SwapTransaction transaction, RedirectAttributes redirectAttributes) {
        User staff = getCurrentStaffUser();
        String redirect = checkStaffStation(staff, redirectAttributes);
        if (redirect != null) return redirect;

        transaction.setStation(staff.getStation());

        try {
            transactionService.createTransaction(transaction);
            redirectAttributes.addFlashAttribute("successMessage", "Xác nhận đổi pin và ghi nhận giao dịch thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }
        return "redirect:/staff/transactions";
    }
}