package com.ev.batteryswap.controllers.admin;

import com.ev.batteryswap.pojo.Battery;
import com.ev.batteryswap.pojo.Station;
import com.ev.batteryswap.pojo.SwapTransaction;
import com.ev.batteryswap.pojo.User;
import com.ev.batteryswap.services.IBatteryService;
import com.ev.batteryswap.services.ITransactionService;
import com.ev.batteryswap.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections; // <-- Thêm import
import java.util.List;
import java.util.Map; // <-- Thêm import
import java.util.Optional;

@Controller
@RequestMapping("/admin/transactions")
public class AdminTransactionController {

    @Autowired
    private ITransactionService transactionService;

    @Autowired
    private IBatteryService batteryService;

    @Autowired
    private IUserService userService;

    // BƯỚC 1: Thêm log chi tiết vào @ModelAttribute
    @ModelAttribute("stations")
    public List<Station> getAllStations(Model model) { // Thêm Model
        try {
            System.out.println("LOG: Đang tải @ModelAttribute('stations')...");
            List<Station> stations = batteryService.getAllStations();
            System.out.println("LOG: Tải 'stations' thành công.");
            return stations;
        } catch (Exception e) {
            System.err.println("### LỖI NGHIÊM TRỌNG: Không thể tải 'stations' trong @ModelAttribute ###");
            e.printStackTrace();
            model.addAttribute("errorMessage", "Lỗi nghiêm trọng khi tải danh sách trạm: " + e.getMessage());
            return Collections.emptyList(); // Trả về danh sách rỗng để tránh crash
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
        Page<SwapTransaction> transactionPage = Page.empty(pageable);
        Map<String, Long> stats = Collections.emptyMap();

        try {
            System.out.println("LOG: Đang tải 'stats' (thống kê giao dịch)...");
            // BƯỚC 2: Tải stats trước
            stats = transactionService.getTransactionStatistics();
            System.out.println("LOG: Tải 'stats' thành công.");

            System.out.println("LOG: Đang lọc giao dịch (filterTransactions)...");
            // BƯỚC 3: Tải trang giao dịch
            transactionPage = transactionService.filterTransactions(stationId, paymentStatus, search, pageable);
            System.out.println("LOG: Lọc giao dịch thành công.");

            model.addAttribute("transactionPage", transactionPage);
            model.addAttribute("stats", stats);

        } catch (Exception e) {
            System.err.println("### LỖI NGHIÊM TRỌNG: Lỗi khi tải trang listTransactions ###");
            e.printStackTrace();
            model.addAttribute("errorMessage", "Đã xảy ra lỗi khi tải dữ liệu: " + e.getMessage());
            model.addAttribute("transactionPage", Page.empty(pageable)); // Đảm bảo trang rỗng
            model.addAttribute("stats", stats); // Vẫn hiển thị stats nếu nó đã tải thành công
        }

        model.addAttribute("selectedStationId", stationId);
        model.addAttribute("selectedPaymentStatus", paymentStatus);
        model.addAttribute("search", search);

        System.out.println("LOG: Hoàn tất, đang render 'admin/battery_transactions.html'");
        return "admin/battery_transactions";
    }

    // Tải "batteries" và "users" CHỈ KHI CẦN VÀO FORM
    private void addFormAttributes(Model model) {
        try {
            System.out.println("LOG: Đang tải 'batteries' cho form...");
            Page<Battery> allBatteries = batteryService.filterBatteries(null, null, null, PageRequest.of(0, Integer.MAX_VALUE));
            model.addAttribute("batteries", allBatteries);
            System.out.println("LOG: Tải 'batteries' cho form thành công.");
        } catch (Exception e) {
            System.err.println("### LỖI: Không thể tải 'batteries' cho form ###");
            e.printStackTrace();
            model.addAttribute("batteries", Page.empty());
        }

        try {
            System.out.println("LOG: Đang tải 'users' cho form...");
            Page<User> allUsers = userService.filterUsers(null, PageRequest.of(0, Integer.MAX_VALUE));
            model.addAttribute("users", allUsers);
            System.out.println("LOG: Tải 'users' cho form thành công.");
        } catch (Exception e) {
            System.err.println("### LỖI: Không thể tải 'users' cho form ###");
            e.printStackTrace();
            model.addAttribute("users", Page.empty());
        }
    }

    @GetMapping("/new")
    public String showCreateTransactionForm(Model model) {
        System.out.println("LOG: Yêu cầu trang /new");
        model.addAttribute("transaction", new SwapTransaction());
        addFormAttributes(model);
        return "admin/transaction_form";
    }

    @PostMapping
    public String createTransaction(@ModelAttribute("transaction") SwapTransaction transaction, RedirectAttributes redirectAttributes) {
        System.out.println("LOG: Yêu cầu POST / (tạo mới)");
        try {
            transactionService.saveTransaction(transaction);
            redirectAttributes.addFlashAttribute("successMessage", "Tạo giao dịch mới thành công!");
        } catch (Exception e) {
            System.err.println("### LỖI: Không thể tạo giao dịch ###");
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi tạo giao dịch: " + e.getMessage());
        }
        return "redirect:/admin/transactions";
    }

    @GetMapping("/edit/{id}")
    public String showEditTransactionForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        System.out.println("LOG: Yêu cầu trang /edit/" + id);
        Optional<SwapTransaction> transaction = transactionService.getTransactionById(id);
        if (transaction.isPresent()) {
            model.addAttribute("transaction", transaction.get());
            addFormAttributes(model);
            return "admin/transaction_form";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy giao dịch!");
        return "redirect:/admin/transactions";
    }

    @PostMapping("/update/{id}")
    public String updateTransaction(@PathVariable("id") Integer id, @ModelAttribute("transaction") SwapTransaction transaction, RedirectAttributes redirectAttributes) {
        System.out.println("LOG: Yêu cầu POST /update/" + id);
        try {
            transaction.setId(id);
            transactionService.saveTransaction(transaction);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật giao dịch thành công!");
        } catch (Exception e) {
            System.err.println("### LỖI: Không thể cập nhật giao dịch ###");
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi cập nhật giao dịch: " + e.getMessage());
        }
        return "redirect:/admin/transactions";
    }

    @GetMapping("/delete/{id}")
    public String deleteTransaction(@PathVariable("id") Integer transactionId, RedirectAttributes redirectAttributes) {
        System.out.println("LOG: Yêu cầu GET /delete/" + transactionId);
        try {
            transactionService.deleteTransaction(transactionId);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa giao dịch thành công!");
        } catch (Exception e) {
            System.err.println("### LỖI: Không thể xóa giao dịch ###");
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa giao dịch: " + e.getMessage());
        }
        return "redirect:/admin/transactions";
    }
}