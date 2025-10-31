package com.ev.batteryswap.services;

import com.ev.batteryswap.pojo.User;
import com.ev.batteryswap.repositories.IUserRepositories;
import com.ev.batteryswap.services.interfaces.IUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServices implements IUserServices {

    @Autowired
    private IUserRepositories userRepositories;

    @Override
    public User findByUsername(String username) {
        return userRepositories.findByUsername(username);
    }

    @Override
    public User findById(int id) {
        return userRepositories.findById(id);
    }
}
