package com.restapi.api.services;

import com.restapi.api.services.interfaces.IRegisterServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.restapi.api.repositories.IRegisterRepositories;
import com.restapi.api.pojo.User;

@Service
public class RegisterServices implements IRegisterServices {

    @Autowired
    private IRegisterRepositories registerRepositories;

    @Override
    public boolean existsByUsername(String username) {
        return registerRepositories.existsByUsername(username);
    }

    @Override
    public User addUser(User user) {
        return registerRepositories.save(user);
    }
}
