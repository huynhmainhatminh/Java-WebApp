package com.ev.batteryswap.services.interfaces;

import com.ev.batteryswap.pojo.RentalPackage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Optional;

public interface IRentalPackageService { // <-- Đã đổi tên
    Page<RentalPackage> findAll(Pageable pageable);
    Map<String, Number> getStatistics();
    Optional<RentalPackage> findById(Integer id);
    RentalPackage save(RentalPackage rentalPackage);
    void deleteById(Integer id);
}