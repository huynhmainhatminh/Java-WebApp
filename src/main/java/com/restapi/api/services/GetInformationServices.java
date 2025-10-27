package com.restapi.api.services;
import com.restapi.api.pojo.User;
import com.restapi.api.repositories.IGetInformation;
import com.restapi.api.services.interfaces.IGetInformationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GetInformationServices implements IGetInformationServices {

    @Autowired
    IGetInformation getInformation;

    public User findByUsername(String username) {
        return getInformation.findByUsername(username);
    }
}
