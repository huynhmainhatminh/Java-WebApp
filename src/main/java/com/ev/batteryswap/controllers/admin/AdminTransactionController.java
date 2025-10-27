package com.ev.batteryswap.controllers.admin;

import com.ev.batteryswap.pojo.Battery; // Thêm import
import com.ev.batteryswap.pojo.Station;
import com.ev.batteryswap.pojo.User;
import com.ev.batteryswap.pojo.SwapTransaction;
import com.ev.batteryswap.services.IAdminBatteryService;
import com.ev.batteryswap.services.IAdminTransactionService;
import com.ev.batteryswap.services.IAdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional; // Thêm import

@Controller
@RequestMapping("/admin/transactions")
public class AdminTransactionController {

    @Autowired
    private IAdminTransactionService adminTransactionService;

    @Autowired
    private IAdminBatteryService adminBatteryService;

    @Autowired // <<< 3. TIÊM VÀO USER SERVICE
    private IAdminUserService adminUserService;

    @ModelAttribute("stations")
    public List<Station> getAllStations() {
        return adminBatteryService.getAllStations();
    }

    @ModelAttribute("batteries")
    public Page<Battery> getAllBatteries() {
        // Lấy tất cả pin để chọn trong form
        return adminBatteryService.filterBatteries(null, null, null, PageRequest.of(0, Integer.MAX_VALUE));
    }

    // <<< 4. THÊM PHƯƠNG THỨC NÀY ĐỂ LẤY DANH SÁCH USER
    @ModelAttribute("users")
    public Page<User> getAllUsers() {
        return adminUserService.filterUsers(null, PageRequest.of(0, Integer.MAX_VALUE));
    }

    @GetMapping
    public String listTransactions(Model model,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "15") int size,
                                   @RequestParam(required = false) Integer stationId,
                                   @RequestParam(required = false) String paymentStatus,
                                   @RequestParam(required = false) String search) {

        Page<SwapTransaction> transactionPage = adminTransactionService.filterTransactions(stationId, paymentStatus, search, PageRequest.of(page, size));

        model.addAttribute("transactionPage", transactionPage);
        model.addAttribute("stats", adminTransactionService.getTransactionStatistics());
        model.addAttribute("selectedStationId", stationId);
        model.addAttribute("selectedPaymentStatus", paymentStatus);
        model.addAttribute("search", search);

        return "admin/battery_transactions";
    }

    // HIỂN THỊ FORM TẠO MỚI
    @GetMapping("/new")
    public String showCreateTransactionForm(Model model) {
        model.addAttribute("transaction", new SwapTransaction());
        return "admin/transaction_form";
    }

    // XỬ LÝ TẠO MỚI
    @PostMapping
    public String createTransaction(@ModelAttribute("transaction") SwapTransaction transaction, RedirectAttributes redirectAttributes) {
        try {
            adminTransactionService.saveTransaction(transaction);
            redirectAttributes.addFlashAttribute("successMessage", "Tạo giao dịch mới thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi tạo giao dịch: " + e.getMessage());
        }

        // SỬA LẠI ĐƯỜNG DẪN CHUYỂN HƯỚNG TẠI ĐÂY
        return "redirect:/admin/transactions";
    }

    // HIỂN THỊ FORM CHỈNH SỬA
    @GetMapping("/edit/{id}")
    public String showEditTransactionForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<SwapTransaction> transaction = adminTransactionService.getTransactionById(id);
        if (transaction.isPresent()) {
            model.addAttribute("transaction", transaction.get());
            return "admin/transaction_form";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy giao dịch!");
        return "redirect:/admin/transactions";
    }

    // XỬ LÝ CẬP NHẬT
    @PostMapping("/update/{id}")
    public String updateTransaction(@PathVariable("id") Integer id, @ModelAttribute("transaction") SwapTransaction transaction, RedirectAttributes redirectAttributes) {
        transaction.setId(id);
        adminTransactionService.saveTransaction(transaction);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật giao dịch thành công!");
        return "redirect:/admin/transactions";
    }

    @GetMapping("/delete/{id}")
    public String deleteTransaction(@PathVariable("id") Integer transactionId, RedirectAttributes redirectAttributes) {
        try {
            adminTransactionService.deleteTransaction(transactionId);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa giao dịch thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa giao dịch: " + e.getMessage());
        }
        return "redirect:/admin/transactions";
    }
}