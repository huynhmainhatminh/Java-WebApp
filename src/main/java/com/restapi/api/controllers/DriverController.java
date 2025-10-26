package com.restapi.api.controllers.DRIVER;
import com.restapi.api.model.requests.RegisterPackageRequests;
import com.restapi.api.pojo.RentalPackage;
import com.restapi.api.pojo.User;
import com.restapi.api.services.GetInformationServices;
import com.restapi.api.services.RegisterPackageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
public class DriverController {

    @Autowired
    RegisterPackageServices registerPackageServices;


    @PostMapping("/registerPackage")
    public RentalPackage registerPack(@ModelAttribute RegisterPackageRequests registerPackageRequests){
        RentalPackage rentalPackage = new RentalPackage();
        User user = new User();
        user.setId(registerPackageRequests.getUserId());
        rentalPackage.setUser(user);
        rentalPackage.setName(registerPackageRequests.getName());
        rentalPackage.setPrice(registerPackageRequests.getPrice());
        rentalPackage.setDurationDays(registerPackageRequests.getDuration_days());
        registerPackageServices.registerPack(rentalPackage);
        return rentalPackage;
    }

}
