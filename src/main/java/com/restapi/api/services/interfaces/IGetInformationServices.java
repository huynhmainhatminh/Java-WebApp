package com.restapi.api.services.interfaces;

import com.restapi.api.pojo.User;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface IGetInformationServices {
    String findByUsername(String username);
    User findById(int id);
    boolean existsByUsername(String username);
    int updateBalanceById(@Param("id") int id, @Param("balance") BigDecimal balance);
}
