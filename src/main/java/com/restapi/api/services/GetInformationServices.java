package com.restapi.api.services;
import com.restapi.api.pojo.User;
import com.restapi.api.repositories.IGetInformation;
import com.restapi.api.services.interfaces.IGetInformationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GetInformationServices implements IGetInformationServices {

    @Autowired
    IGetInformation getInformation;

<<<<<<< HEAD

    public boolean existsByUsername(String username) {
        return getInformation.existsByUsername(username);
    }


    public String findByUsername(String username) {
        return getInformation.findByUsername(username).getUsername();
=======
    public User findByUsername(String username) {
        return getInformation.findByUsername(username);
>>>>>>> 4b548f1d7bc27aa26725a049737f7e08babeccc2
    }
}
