package com.restapi.api.services;

import com.restapi.api.repositories.IDeletePackageRepositories;
import com.restapi.api.services.interfaces.IDeletePackageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DeletePackageServices implements IDeletePackageServices {

    @Autowired
    IDeletePackageRepositories iDeletePackageRepositories;

    public boolean existsByUser_id(Integer userId) {
        return iDeletePackageRepositories.existsByUser_id(userId);
    }

    public void deleteByUser_id(Integer userId) {
        iDeletePackageRepositories.deleteByUser_id(userId);
    }
}
