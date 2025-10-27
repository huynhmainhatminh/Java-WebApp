package com.restapi.api.services.interfaces;

import com.restapi.api.pojo.User;

public interface IGetInformationServices {
    User findByUsername(String username);
}
