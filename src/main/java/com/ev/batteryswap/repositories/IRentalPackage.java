package com.ev.batteryswap.repositories;

import com.ev.batteryswap.pojo.RentalPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRentalPackage extends JpaRepository<RentalPackage, Integer> {
}
