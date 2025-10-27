
package com.ev.batteryswap.repositories;

import com.ev.batteryswap.pojo.SwapTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List; // Thêm import
import java.util.Map; // Thêm import

@Repository
public interface SwapTransactionRepository extends JpaRepository<SwapTransaction, Integer>, JpaSpecificationExecutor<SwapTransaction> {
    long countByPaymentStatus(String paymentStatus);

    @Query("SELECT FUNCTION('HOUR', s.createdAt) as hour, COUNT(s) as count FROM SwapTransaction s GROUP BY hour ORDER BY hour ASC")
    List<Map<String, Object>> countTransactionsByHour();
}