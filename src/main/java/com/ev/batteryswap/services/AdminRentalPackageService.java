package com.ev.batteryswap.services;

import com.ev.batteryswap.pojo.RentalPackage;
import com.ev.batteryswap.repositories.RentalPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AdminRentalPackageService implements IAdminRentalPackageService {

    @Autowired
    private RentalPackageRepository rentalPackageRepository;

    @Override
    public Page<RentalPackage> findAll(Pageable pageable) {
        return rentalPackageRepository.findAll(pageable);
    }

    @Override
    public Map<String, Number> getStatistics() {
        Map<String, Number> stats = new HashMap<>();
        stats.put("total_packages", rentalPackageRepository.count());
        stats.put("active_packages", rentalPackageRepository.countByStatus("ACTIVE"));
        stats.put("avg_price", rentalPackageRepository.getAveragePrice() != null ? rentalPackageRepository.getAveragePrice() : 0);
        stats.put("avg_duration", rentalPackageRepository.getAverageDuration() != null ? rentalPackageRepository.getAverageDuration() : 0);
        return stats;
    }

    @Override
    public Optional<RentalPackage> findById(Integer id) {
        return rentalPackageRepository.findById(id);
    }

    @Override
    public RentalPackage save(RentalPackage rentalPackage) {
        return rentalPackageRepository.save(rentalPackage);
    }

    @Override
    public void deleteById(Integer id) {
        rentalPackageRepository.deleteById(id);
    }
}