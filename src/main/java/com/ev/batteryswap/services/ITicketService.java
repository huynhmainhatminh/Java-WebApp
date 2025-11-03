package com.ev.batteryswap.services;

import com.ev.batteryswap.pojo.SupportTicket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.Optional;

public interface ITicketService {
    Page<SupportTicket> filterTickets(String status, String priority, String search, Pageable pageable);
    Map<String, Long> getTicketStatistics();
    Optional<SupportTicket> findById(Integer id);
    SupportTicket save(SupportTicket ticket);
    void deleteById(Integer id);
}