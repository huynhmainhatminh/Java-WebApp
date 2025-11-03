package com.ev.batteryswap.controllers.admin;

import com.ev.batteryswap.pojo.SupportTicket;
import com.ev.batteryswap.services.ITicketService; // <-- Đã đổi tên
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Instant;
import java.util.Optional;

@Controller
@RequestMapping("/admin/tickets")
public class AdminTicketController {

    @Autowired
    private ITicketService ticketService; // <-- Đã đổi tên

    @GetMapping
    public String listTickets(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(required = false) String status,
                              @RequestParam(required = false) String priority,
                              @RequestParam(required = false) String search) {
        Page<SupportTicket> ticketPage = ticketService.filterTickets(status, priority, search, PageRequest.of(page, 15)); // <-- Đã đổi tên
        model.addAttribute("ticketPage", ticketPage);
        model.addAttribute("stats", ticketService.getTicketStatistics()); // <-- Đã đổi tên
        model.addAttribute("selectedStatus", status);
        model.addAttribute("selectedPriority", priority);
        model.addAttribute("search", search);
        return "admin/tickets";
    }

    @GetMapping("/{id}")
    public String viewTicket(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<SupportTicket> ticket = ticketService.findById(id); // <-- Đã đổi tên
        if (ticket.isPresent()) {
            model.addAttribute("ticket", ticket.get());
            return "admin/ticket_detail";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy ticket!");
        return "redirect:/admin/tickets";
    }

    @PostMapping("/update/{id}")
    public String updateTicket(@PathVariable("id") Integer id,
                               @ModelAttribute SupportTicket formData,
                               RedirectAttributes redirectAttributes) {
        Optional<SupportTicket> ticketOptional = ticketService.findById(id); // <-- Đã đổi tên
        if (ticketOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ticket không tồn tại.");
            return "redirect:/admin/tickets";
        }
        SupportTicket ticket = ticketOptional.get();
        ticket.setStatus(formData.getStatus());
        ticket.setPriority(formData.getPriority());
        ticket.setAdminResponse(formData.getAdminResponse());
        ticket.setUpdatedAt(Instant.now());

        ticketService.save(ticket); // <-- Đã đổi tên
        redirectAttributes.addFlashAttribute("successMessage", "Đã cập nhật ticket!");
        return "redirect:/admin/tickets/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteTicket(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        ticketService.deleteById(id); // <-- Đã đổi tên
        redirectAttributes.addFlashAttribute("successMessage", "Đã xóa ticket thành công!");
        return "redirect:/admin/tickets";
    }
}