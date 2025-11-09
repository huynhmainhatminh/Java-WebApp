package com.ev.batteryswap.services;
import com.ev.batteryswap.pojo.User;
import com.ev.batteryswap.repositories.IUserRepositories;
import com.ev.batteryswap.services.interfaces.IAuthServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthServices implements IAuthServices {

    @Autowired
    private IUserRepositories userRepositories;

    @Override
    public boolean login(String username, String password) {
        return userRepositories.existsByUsernameAndPassword(username, password);
    }

    @Override
    public User register(User user) {
        return userRepositories.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepositories.existsByUsername(username);
    }

}
