package com.ev.batteryswap.services;

import com.ev.batteryswap.pojo.Station;
import com.ev.batteryswap.repositories.StationRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AdminStationService implements IAdminStationService {

    @Autowired
    private StationRepository stationRepository;

    @Override
    public Page<Station> filterStations(String search, Pageable pageable) {
        return stationRepository.findAll((Specification<Station>) (root, query, cb) -> {
            if (search != null && !search.trim().isEmpty()) {
                String likePattern = "%" + search.toLowerCase() + "%";
                return cb.or(
                        cb.like(cb.lower(root.get("name")), likePattern),
                        cb.like(cb.lower(root.get("address")), likePattern)
                );
            }
            return cb.conjunction();
        }, pageable);
    }

    @Override
    public Map<String, Long> getStationStatistics() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("total_stations", stationRepository.count());
        stats.put("active_stations", stationRepository.countByStatus("ACTIVE"));
        stats.put("maintenance_stations", stationRepository.countByStatus("MAINTENANCE"));
        stats.put("inactive_stations", stationRepository.countByStatus("INACTIVE")); // Giả sử có trạng thái này
        return stats;
    }

    @Override
    public Station saveStation(Station station) {
        return stationRepository.save(station);
    }

    @Override
    public Optional<Station> getStationById(Integer id) {
        return stationRepository.findById(id);
    }

    @Override
    public void deleteStation(Integer id) {
        stationRepository.deleteById(id);
    }
}