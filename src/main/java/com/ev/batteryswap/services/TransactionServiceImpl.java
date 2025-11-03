package com.ev.batteryswap.services;

import com.ev.batteryswap.pojo.Battery;
import com.ev.batteryswap.pojo.SwapTransaction;
import com.ev.batteryswap.repositories.BatteryRepository;
import com.ev.batteryswap.repositories.SwapTransactionRepository;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionServiceImpl implements ITransactionService { // <-- Đã đổi tên

    @Autowired
    private SwapTransactionRepository transactionRepository;
    @Autowired
    private BatteryRepository batteryRepository;

    @Override
    public Page<SwapTransaction> filterTransactions(Integer stationId, String paymentStatus, String search, Pageable pageable) {
        return transactionRepository.findAll((Specification<SwapTransaction>) (root, query, cb) -> {
            if (query.getResultType() != Long.class && query.getResultType() != long.class) {
                root.fetch("station", JoinType.LEFT);
                root.fetch("batteryIn", JoinType.LEFT);
                root.fetch("batteryOut", JoinType.LEFT);
            }

            List<Predicate> predicates = new ArrayList<>();
            if (stationId != null) {
                predicates.add(cb.equal(root.get("station").get("id"), stationId));
            }
            if (paymentStatus != null && !paymentStatus.isEmpty()) {
                predicates.add(cb.equal(root.get("paymentStatus"), paymentStatus));
            }
            if (search != null && !search.trim().isEmpty()) {
                try {
                    Integer transactionId = Integer.parseInt(search);
                    predicates.add(cb.equal(root.get("id"), transactionId));
                } catch (NumberFormatException e) {
                    // Nếu không phải số, có thể bỏ qua hoặc log lỗi
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        }, pageable);
    }

    @Override
    public Map<String, Long> getTransactionStatistics() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("total_transactions", transactionRepository.count());
        stats.put("completed_transactions", transactionRepository.countByPaymentStatus("COMPLETED"));
        stats.put("pending_transactions", transactionRepository.countByPaymentStatus("PENDING"));
        stats.put("failed_transactions", transactionRepository.countByPaymentStatus("FAILED"));
        return stats;
    }

    @Override
    public void deleteTransaction(Integer transactionId) {
        transactionRepository.deleteById(transactionId);
    }

    @Override
    public void saveTransaction(SwapTransaction transaction) {
        // Lấy thông tin đầy đủ của 2 viên pin từ DB
        Battery batteryIn = batteryRepository.findById(transaction.getBatteryIn().getId())
                .orElseThrow(() -> new RuntimeException("Pin trả vào không tồn tại!"));

        Battery batteryOut = batteryRepository.findById(transaction.getBatteryOut().getId())
                .orElseThrow(() -> new RuntimeException("Pin lấy ra không tồn tại!"));

        // === BẮT ĐẦU LOGIC NGHIỆP VỤ THỰC TẾ ===

        // 1. Kiểm tra logic: Pin lấy ra phải đang "AVAILABLE"
        if (!"AVAILABLE".equals(batteryOut.getStatus())) {
            throw new RuntimeException("Logic lỗi: Pin lấy ra " +
                    "(" + batteryOut.getSerialNumber() + ") không ở trạng thái Sẵn sàng!");
        }

        // 2. Tự động cập nhật trạng thái của các viên pin
        batteryIn.setStatus("CHARGING"); // Hoặc "MAINTENANCE" tùy vào logic kiểm tra pin
        batteryIn.setStation(transaction.getStation()); // Cập nhật vị trí mới cho pin cũ

        batteryOut.setStatus("RENTED"); // Pin mới giờ đã được cho thuê
        batteryOut.setStation(null); // Pin mới không còn ở trạm nào nữa

        // 3. Lưu lại trạng thái mới của 2 viên pin
        batteryRepository.save(batteryIn);
        batteryRepository.save(batteryOut);

        // 4. Lưu bản ghi giao dịch
        transactionRepository.save(transaction);
    }

    @Override
    public Optional<SwapTransaction> getTransactionById(Integer id) {
        return transactionRepository.findById(id);
    }

    @Override
    public List<Map<String, Object>> getHourlySwapReport() {
        return transactionRepository.countTransactionsByHour();
    }
}