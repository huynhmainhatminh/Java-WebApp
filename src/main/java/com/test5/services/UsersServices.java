package com.test5.services;

import com.test5.pojo.User;
import com.test5.repositories.IUsersRepositories;
import com.test5.repositories.UsersRepositories;
import org.springframework.stereotype.Service;

@Service
public class UsersServices implements IUsersServices {

    private IUsersRepositories usersrepositories;

    public UsersServices() {
        usersrepositories = new UsersRepositories();
    }

    @Override
    public User findUserById(int id) {
        return usersrepositories.findUserById(id);
    }
    @Override
    public User findUserByUsername(String username) {
        return usersrepositories.findUserByUsername(username);
    }

    @Override
    public boolean checkLogin(String username, String password) {
        return usersrepositories.checkLogin(username,password);
    }

}
