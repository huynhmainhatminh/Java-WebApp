package com.ev.batteryswap.services.interfaces;

import com.ev.batteryswap.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {
    Page<User> filterUsers(String searchKeyword, Pageable pageable);
    void updateUserRole(Integer userId, String role);
    void saveUser(User user);
    void deleteUser(Integer userId);
    User findById(Integer userId);
    //Tìm nhân viên dựa theo id trạm
    List<User> getStaffByStation(Integer stationId);
    List<User> getUsersByRole(String role);
}