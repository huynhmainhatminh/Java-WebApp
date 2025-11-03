package com.ev.batteryswap.services;

import com.ev.batteryswap.pojo.RentalPackage;
import com.ev.batteryswap.repositories.IRentalPackage;
import com.ev.batteryswap.services.interfaces.IRentalPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class RentalPackageService implements IRentalPackageService { // <-- Đã đổi tên

    @Autowired
    private IRentalPackage iRentalPackage;

    @Override
    public Page<RentalPackage> findAll(Pageable pageable) {
        return iRentalPackage.findAll(pageable);
    }

    @Override
    public Map<String, Number> getStatistics() {
        Map<String, Number> stats = new HashMap<>();
        stats.put("total_packages", iRentalPackage.count());
        stats.put("active_packages", iRentalPackage.countByStatus("ACTIVE"));
        stats.put("avg_price", iRentalPackage.getAveragePrice() != null ? iRentalPackage.getAveragePrice() : 0);
        stats.put("avg_duration", iRentalPackage.getAverageDuration() != null ? iRentalPackage.getAverageDuration() : 0);
        return stats;
    }

    @Override
    public Optional<RentalPackage> findById(Integer id) {
        return iRentalPackage.findById(id);
    }

    @Override
    public RentalPackage save(RentalPackage rentalPackage) {
        return iRentalPackage.save(rentalPackage);
    }

    @Override
    public void deleteById(Integer id) {
        iRentalPackage.deleteById(id);
    }
}