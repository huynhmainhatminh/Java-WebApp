package com.restapi.api.services;


import com.restapi.api.repositories.ILoginRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServices {
    @Autowired
    ILoginRepositories loginRepositories;


    public boolean login(String username, String password) {
        return loginRepositories.existsByUsernameAndPassword(username, password);
    }
}
