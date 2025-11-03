package com.ev.batteryswap.services.interfaces;


import com.ev.batteryswap.pojo.RentalPackage;
import com.ev.batteryswap.pojo.User;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;


public interface IUserServices {
    User findByUsername(String username);
    User findById(int id);
    RentalPackage registerPackage(RentalPackage rentalPackage);

    int updateBalanceById(int userId, BigDecimal price);


}
