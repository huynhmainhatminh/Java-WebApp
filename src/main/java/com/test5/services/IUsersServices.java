package com.test5.services;

import com.test5.pojo.User;

public interface IUsersServices {
    User findUserById(int id);
    User findUserByUsername(String username);
    boolean checkLogin(String username, String password);
}
