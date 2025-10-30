package com.restapi.api.services;
import com.restapi.api.pojo.User;
import com.restapi.api.repositories.IGetInformation;
import com.restapi.api.services.interfaces.IGetInformationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;


@Service
public class GetInformationServices implements IGetInformationServices {

    @Autowired
    IGetInformation getInformation;


    public boolean existsByUsername(String username) {
        return getInformation.existsByUsername(username);
    }


    public String findByUsername(String username) {
        return getInformation.findByUsername(username).getUsername();
    }
    public User findById(int id) {
        return getInformation.findById(id);
    }

    public int updateBalanceById(@Param("id") int id, @Param("balance") BigDecimal balance) {
        return getInformation.updateBalanceById(id, balance);
    }
}
