package com.ev.batteryswap.repositories;

import com.ev.batteryswap.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    // Tìm nhân viên(Staff) theo id trạm và vai trò staff)
    List<User> findByStation_IdAndRole(Integer stationId, String role);
}