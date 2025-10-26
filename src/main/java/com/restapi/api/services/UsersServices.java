package com.restapi.api.services;


import com.restapi.api.pojo.User;
import com.restapi.api.repositories.IUsersRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServices {

    @Autowired
    IUsersRepositories usersRepositories;


    public User findByUsername(String username) {
        return usersRepositories.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return usersRepositories.findAll();
    }

    public void deleteByUsername(String username) {
        usersRepositories.deleteByUsername(username);
    }


}
