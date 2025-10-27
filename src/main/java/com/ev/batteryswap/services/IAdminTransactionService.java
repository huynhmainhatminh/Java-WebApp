package com.ev.batteryswap.services;

import com.ev.batteryswap.pojo.SwapTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IAdminTransactionService {
    Page<SwapTransaction> filterTransactions(Integer stationId, String paymentStatus, String search, Pageable pageable);
    Map<String, Long> getTransactionStatistics();
    void deleteTransaction(Integer transactionId);
    void saveTransaction(SwapTransaction transaction);
    Optional<SwapTransaction> getTransactionById(Integer id);
    List<Map<String, Object>> getHourlySwapReport();
}