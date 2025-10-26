package com.restapi.api.services;
import com.restapi.api.pojo.User;
import com.restapi.api.repositories.IUsersRepositories;
import com.restapi.api.services.interfaces.IUsersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServices implements IUsersServices {

    @Autowired
    IUsersRepositories usersRepositories;


    public List<User> getAllUsers() {
        return usersRepositories.findAll();
    }

    public void deleteByUsername(String username) {
        usersRepositories.deleteByUsername(username);
    }


}
