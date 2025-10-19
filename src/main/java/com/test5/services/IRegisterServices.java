package com.test5.services;

import com.test5.pojo.User;

public interface IRegisterServices {

    void addUser(User user);
    boolean usernameExists(String username);


}
