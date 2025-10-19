package com.test5.services;

import com.test5.pojo.User;
import com.test5.repositories.IRegisterRepositories;
import com.test5.repositories.RegisterRepositories;
import org.springframework.stereotype.Service;


@Service
public class RegisterServices implements IRegisterRepositories {

    RegisterRepositories registerRepositories = new RegisterRepositories();

    @Override
    public void addUser(User user){
        registerRepositories.addUser(user);
    }

    @Override
    public boolean usernameExists(String username) {
        return registerRepositories.usernameExists(username);
    }

}
