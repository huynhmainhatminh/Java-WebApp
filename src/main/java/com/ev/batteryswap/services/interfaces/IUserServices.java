package com.ev.batteryswap.services.interfaces;


import com.ev.batteryswap.pojo.User;


public interface IUserServices {
    User findByUsername(String username);
    User findById(int id);

}
