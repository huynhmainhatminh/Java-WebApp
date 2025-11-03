package com.ev.batteryswap.services;
import com.ev.batteryswap.pojo.Battery;
import com.ev.batteryswap.pojo.Station;
import com.ev.batteryswap.repositories.BatteryRepository;
import com.ev.batteryswap.repositories.StationRepository;
import com.ev.batteryswap.services.interfaces.IBatteryService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BatteryService implements IBatteryService { // <-- Đã đổi tên
    @Autowired
    private BatteryRepository batteryRepository;

    @Autowired
    private StationRepository stationRepository;

    @Override
    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }

    @Override
    public Page<Battery> filterBatteries(Integer stationId, String status, String nameKeyword, Pageable pageable) {
        return batteryRepository.findAll((Specification<Battery>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // Điều kiện 1: Lọc theo Trạm (stationId)
            if (stationId != null) {
                predicates.add(criteriaBuilder.equal(root.get("station").get("id"), stationId));
            }
            // Điều kiện 2: Lọc theo Trạng thái (status)
            if (status != null && !status.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }
            // Điều kiện 3: Lọc theo Tên pin (serialNumber)
            if (nameKeyword != null && !nameKeyword.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("serialNumber")), "%" + nameKeyword.toLowerCase() + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable);
    }


    @Override
    public Optional<Battery> getBatteryById(Integer id) {
        return batteryRepository.findById(id);
    }

    @Override
    public void saveBattery(Battery battery) {
        batteryRepository.save(battery);
    }

    @Override
    public void deleteBattery(Integer id) {
        batteryRepository.deleteById(id);
    }

    @Override
    public Map<String, Long> getBatteryStatistics() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("total", batteryRepository.count());
        stats.put("available", batteryRepository.countByStatus("AVAILABLE"));
        stats.put("charging", batteryRepository.countByStatus("CHARGING"));
        stats.put("maintenance", batteryRepository.countByStatus("MAINTENANCE"));
        stats.put("empty", batteryRepository.countByStatus("EMPTY"));
        stats.put("rented", batteryRepository.countByStatus("RENTED"));
        return stats;
    }

    //Dùng để đưa tt cho phần station
    @Override
    public Map<String, Long> getBatteryStatisticsForStation(Station station) {
        Map<String, Long> stats = new HashMap<>();
        if (station == null) return stats;

        stats.put("total", batteryRepository.countByStation(station));
        stats.put("available", batteryRepository.countByStationAndStatus(station, "AVAILABLE"));
        stats.put("charging", batteryRepository.countByStationAndStatus(station, "CHARGING"));
        stats.put("maintenance", batteryRepository.countByStationAndStatus(station, "MAINTENANCE"));
        stats.put("empty", batteryRepository.countByStationAndStatus(station, "EMPTY"));
        // rent rồi thì không còn ở trạm nữa
        stats.put("rented", 0L);
        return stats;
    }
}