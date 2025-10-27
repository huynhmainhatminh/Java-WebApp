package com.ev.batteryswap.services;

import com.ev.batteryswap.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAdminUserService {
    Page<User> filterUsers(String searchKeyword, Pageable pageable);
    void updateUserRole(Integer userId, String role);
    void saveUser(User user);
    void deleteUser(Integer userId);
}