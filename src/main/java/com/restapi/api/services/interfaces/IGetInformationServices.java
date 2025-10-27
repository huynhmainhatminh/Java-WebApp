package com.restapi.api.services.interfaces;

import com.restapi.api.pojo.User;

public interface IGetInformationServices {
    String findByUsername(String username);
    boolean existsByUsername(String username);
}
