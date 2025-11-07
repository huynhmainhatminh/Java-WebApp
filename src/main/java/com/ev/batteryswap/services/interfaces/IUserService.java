package com.ev.batteryswap.services.interfaces;

import com.ev.batteryswap.pojo.RentalPackage;
import com.ev.batteryswap.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface IUserService {

    Page<User> filterUsers(String searchKeyword, Pageable pageable);
    void updateUserRole(Integer userId, String role);
    void saveUser(User user);
    void deleteUser(Integer userId);
    User findById(Integer userId);



    User findByUsername(String email);
    RentalPackage registerPackage(RentalPackage rentalPackage);
    int updateBalanceById(int userId, BigDecimal price);

}