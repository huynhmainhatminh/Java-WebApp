package com.ev.batteryswap.services;

import com.ev.batteryswap.pojo.Battery;
import com.ev.batteryswap.pojo.Station;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IAdminBatteryService {

    // Phương thức chính để lọc pin
    Page<Battery> filterBatteries(Integer stationId, String status, String nameKeyword, Pageable pageable);

    // Lấy tất cả trạm cho dropdown
    List<Station> getAllStations();

    // Các phương thức CRUD và thống kê
    Optional<Battery> getBatteryById(Integer id);
    void saveBattery(Battery battery);
    void deleteBattery(Integer id);
    Map<String, Long> getBatteryStatistics();

    // THÊM PHƯƠNG THỨC MỚI
    Map<String, Long> getBatteryStatisticsForStation(Station station);
}