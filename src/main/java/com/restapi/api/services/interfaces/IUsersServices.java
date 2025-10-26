package com.restapi.api.services.interfaces;
import com.restapi.api.pojo.User;
import java.util.List;

public interface IUsersServices {
    List<User> getAllUsers();
    void deleteByUsername(String username);
}
