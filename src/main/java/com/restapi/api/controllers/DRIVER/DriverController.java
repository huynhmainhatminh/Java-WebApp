package com.restapi.api.controllers.DRIVER;
import com.restapi.api.model.requests.RegisterPackageRequests;
import com.restapi.api.model.response.ResponseAPI;
import com.restapi.api.pojo.RentalPackage;
import com.restapi.api.pojo.User;
import com.restapi.api.repositories.IDeletePackageRepositories;
import com.restapi.api.services.GetInformationServices;
import com.restapi.api.services.RegisterPackageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
public class DriverController {

    @Autowired
    RegisterPackageServices registerPackageServices;

    @Autowired
    GetInformationServices getInformation;

    @Autowired
    IDeletePackageRepositories iDeletePackageRepositories;


    @PostMapping("/registerPackage")
    public ResponseEntity<?> registerPack(@ModelAttribute RegisterPackageRequests registerPackageRequests){
        try{
            RentalPackage rentalPackage = new RentalPackage();
            User user = new User();
            user.setId(registerPackageRequests.getUserId());
            rentalPackage.setUser(user);
            rentalPackage.setName(registerPackageRequests.getName());
            rentalPackage.setPrice(registerPackageRequests.getPrice());
            rentalPackage.setDurationDays(registerPackageRequests.getDuration_days());
            registerPackageServices.registerPack(rentalPackage);
            return ResponseEntity.ok(
                    new ResponseAPI(true, "Đăng ký dịch vụ thành công")
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new ResponseAPI(false, "Đăng ký dịch vụ thất bại")
            );
        }
    }


    @PostMapping("/information")
    public ResponseEntity<?> information(@RequestParam(value = "username") String username) {
        if (getInformation.existsByUsername(username)) {
            return ResponseEntity.ok(
                    new ResponseAPI(true, getInformation.findByUsername(username))
            );
        } else {
            return ResponseEntity.badRequest().body(
                    new ResponseAPI(false, "Không có thông tin")
            );
        }
        // return getInformation.findByUsername(username);
    }

    @PostMapping("/deletePackage")
    public ResponseEntity<?> deletePack(@RequestParam(value = "userId") Integer userId) {
        if (iDeletePackageRepositories.existsByUser_id(userId)) {
            iDeletePackageRepositories.deleteByUser_id(userId);
            return ResponseEntity.ok(
                    new ResponseAPI(true, "Xóa dịch vụ thành công!")
            );
        } else {
            return ResponseEntity.badRequest().body(
                    new ResponseAPI(false, "Đã xảy ra lỗi!")
            );
        }


    }

}
