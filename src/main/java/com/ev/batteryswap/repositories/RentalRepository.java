package com.ev.batteryswap.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ev.batteryswap.pojo.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {

}
