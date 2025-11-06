package com.ev.batteryswap.controllers.admin;

import com.ev.batteryswap.pojo.Battery;
import com.ev.batteryswap.pojo.Station;
import com.ev.batteryswap.pojo.SwapTransaction;
import com.ev.batteryswap.pojo.User;
import com.ev.batteryswap.services.interfaces.IBatteryService;
import com.ev.batteryswap.services.interfaces.ITransactionService;
import com.ev.batteryswap.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/admin/transactions")
public class AdminTransactionController {

    @Autowired
    private ITransactionService transactionService;
    @Autowired
    private IBatteryService batteryService;

    @Autowired
    private IUserService userService;

    @ModelAttribute("stations")
    public List<Station> getAllStations(Model model) {
        try {
            return batteryService.getAllStations();
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Lỗi nghiêm trọng khi tải danh sách trạm: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @GetMapping
    public String listTransactions(Model model,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "15") int size,
                                   @RequestParam(required = false) Integer stationId,
                                   @RequestParam(required = false) String paymentStatus,
                                   @RequestParam(required = false) String search) {

        Pageable pageable = PageRequest.of(page, size);
        try {
            Map<String, Long> stats = transactionService.getTransactionStatistics();
            Page<SwapTransaction> transactionPage = transactionService.filterTransactions(stationId, paymentStatus, search, pageable);

            model.addAttribute("transactionPage", transactionPage);
            model.addAttribute("stats", stats);

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Đã xảy ra lỗi khi tải dữ liệu: " + e.getMessage());
            model.addAttribute("transactionPage", Page.empty(pageable));
            model.addAttribute("stats", Collections.emptyMap());
        }

        model.addAttribute("selectedStationId", stationId);
        model.addAttribute("selectedPaymentStatus", paymentStatus);
        model.addAttribute("search", search);

        return "admin/battery_transactions";
    }

    private void addFormAttributes(Model model) {
        try {
            Page<Battery> allBatteries = batteryService.filterBatteries(null, null, null, PageRequest.of(0, Integer.MAX_VALUE));
            model.addAttribute("batteries", allBatteries);
        } catch (Exception e) {
            model.addAttribute("batteries", Page.empty());
        }

        try {
            Page<User> allUsers = userService.filterUsers(null, PageRequest.of(0, Integer.MAX_VALUE));
            model.addAttribute("users", allUsers);
        } catch (Exception e) {
            model.addAttribute("users", Page.empty());
        }
    }

    @GetMapping("/new")
    public String showCreateTransactionForm(Model model) {
        model.addAttribute("transaction", new SwapTransaction());
        addFormAttributes(model);
        return "admin/transaction_form";
    }

    @PostMapping
    public String createTransaction(@ModelAttribute("transaction") SwapTransaction transaction, RedirectAttributes redirectAttributes) {
        try {
            transactionService.saveTransaction(transaction);
            redirectAttributes.addFlashAttribute("successMessage", "Tạo giao dịch mới thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi tạo giao dịch: " + e.getMessage());
        }
        return "redirect:/admin/transactions";
    }

    @GetMapping("/edit/{id}")
    public String showEditTransactionForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        SwapTransaction transaction = transactionService.getTransactionById(id);
        if (transaction != null) { // Sửa từ .isPresent()
            model.addAttribute("transaction", transaction); // Sửa từ .get()
            addFormAttributes(model);
            return "admin/transaction_form";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy giao dịch!");
        return "redirect:/admin/transactions";
    }

    @PostMapping("/update/{id}")
    public String updateTransaction(@PathVariable("id") Integer id,
                                    @ModelAttribute("transaction") SwapTransaction transactionFormData,
                                    RedirectAttributes redirectAttributes) {
        try {
            SwapTransaction existingTransaction = transactionService.getTransactionById(id);
            if (existingTransaction == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy giao dịch để cập nhật!");
                return "redirect:/admin/transactions";
            }

            // Cập nhật các trường từ form
            existingTransaction.setStation(transactionFormData.getStation());
            existingTransaction.setBatteryIn(transactionFormData.getBatteryIn());
            existingTransaction.setBatteryOut(transactionFormData.getBatteryOut());
            existingTransaction.setAmount(transactionFormData.getAmount());
            existingTransaction.setPaymentMethod(transactionFormData.getPaymentMethod());
            existingTransaction.setPaymentStatus(transactionFormData.getPaymentStatus());
            existingTransaction.setNotes(transactionFormData.getNotes());

            transactionService.saveTransaction(existingTransaction);

            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật giao dịch thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi cập nhật giao dịch: " + e.getMessage());
        }
        return "redirect:/admin/transactions";
    }


    @GetMapping("/delete/{id}")
    public String deleteTransaction(@PathVariable("id") Integer transactionId, RedirectAttributes redirectAttributes) {
        try {
            transactionService.deleteTransaction(transactionId);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa giao dịch thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa giao dịch: " + e.getMessage());
        }
        return "redirect:/admin/transactions";
    }
}