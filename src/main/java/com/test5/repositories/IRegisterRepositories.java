package com.test5.repositories;

import com.test5.pojo.User;

public interface IRegisterRepositories {

    void addUser(User user);
    boolean usernameExists(String username);

}
