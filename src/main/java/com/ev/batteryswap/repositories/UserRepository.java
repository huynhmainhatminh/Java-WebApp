package com.ev.batteryswap.repositories;

import com.ev.batteryswap.pojo.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    boolean existsByUsernameAndPassword(String username, String password);
    boolean existsByUsername(String username);

    User findByUsername(String username);
    User findByRole(String role);
    User findById(int id);

    @Transactional
    @Modifying()
    @Query("UPDATE User u SET u.walletBalance = :balance WHERE u.id = :id")
    int updateBalanceById(int id, BigDecimal balance);

}