package com.restapi.api.services;


import com.restapi.api.pojo.RentalPackage;
import com.restapi.api.repositories.IRegisterPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterPackageServices {

    @Autowired
    IRegisterPackage registerPackage;

    public RentalPackage registerPack(RentalPackage rentalPackage) {
        return registerPackage.save(rentalPackage);
    }

}
