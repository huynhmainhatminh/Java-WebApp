package com.test5.repositories;

import com.test5.pojo.User;

public interface IUsersRepositories {
    User findUserById(int id);
    User findUserByUsername(String username);
    boolean checkLogin(String username, String password);
}
