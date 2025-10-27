package com.ev.batteryswap.repositories;

import com.ev.batteryswap.pojo.Battery;
import com.ev.batteryswap.pojo.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BatteryRepository extends JpaRepository<Battery, Integer>, JpaSpecificationExecutor<Battery> {
    long countByStatus(String status);

    // lấy dữ liệu pin cho cho station
    long countByStation(Station station);
    long countByStationAndStatus(Station station, String status);
}