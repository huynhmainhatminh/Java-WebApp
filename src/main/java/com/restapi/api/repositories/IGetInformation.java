package com.restapi.api.repositories;

import com.restapi.api.pojo.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


@Repository
public interface IGetInformation extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findById(int id);
    boolean existsByUsername(String username);

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE User u SET u.walletBalance = :balance WHERE u.id = :id")
    int updateBalanceById(@Param("id") int id, @Param("balance") BigDecimal balance);
}
