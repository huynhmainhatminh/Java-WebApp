package com.ev.batteryswap.services.interfaces;

import com.ev.batteryswap.pojo.Battery;
import com.ev.batteryswap.pojo.Station;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface IBatteryService {
    Page<Battery> filterBatteries(Integer stationId, String status, String nameKeyword, Pageable pageable);
    List<Station> getAllStations();
    void saveBattery(Battery battery);
    void deleteBattery(Integer id);
    Map<String, Long> getBatteryStatistics();
    Map<String, Long> getBatteryStatisticsForStation(Station station);
    Battery getBatteryById(Integer id);
}