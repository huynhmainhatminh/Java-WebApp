package com.ev.batteryswap.services.interfaces;

import com.ev.batteryswap.pojo.Station;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Optional;

public interface IStationService { // <-- Đã đổi tên
    Page<Station> filterStations(String search, Pageable pageable);
    Map<String, Long> getStationStatistics();
    Station saveStation(Station station);
    Optional<Station> getStationById(Integer id);
    void deleteStation(Integer id);
}
