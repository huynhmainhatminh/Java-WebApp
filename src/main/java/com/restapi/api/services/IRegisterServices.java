package com.restapi.api.services;

import com.restapi.api.pojo.User;

public interface IRegisterServices {
    boolean existsByUsername(String username);
    User addUser(User user);
}
