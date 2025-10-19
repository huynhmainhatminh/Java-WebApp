package com.test5.services;

import com.test5.repositories.LoginRepositories;
import org.springframework.stereotype.Service;


@Service
public class LoginServices implements ILoginServices {

    LoginRepositories loginRepositories = new LoginRepositories();

    @Override
    public boolean checkLogin(String username, String password) {
        return loginRepositories.checkLogin(username, password);
    }
}
