package com.restapi.api.controllers.DRIVER;
import com.restapi.api.model.requests.RegisterPackageRequests;
import com.restapi.api.model.response.ResponseAPI;
import com.restapi.api.pojo.Battery;
import com.restapi.api.pojo.RentalPackage;
import com.restapi.api.pojo.User;
import com.restapi.api.repositories.IDeletePackageRepositories;
import com.restapi.api.services.DangkyDoipinServices;
import com.restapi.api.services.GetInformationServices;
import com.restapi.api.services.RegisterPackageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/user")
public class DriverController {

    @Autowired
    RegisterPackageServices registerPackageServices;

    @Autowired
    GetInformationServices getInformation;

    @Autowired
    IDeletePackageRepositories iDeletePackageRepositories;

    @Autowired
    DangkyDoipinServices dangkyDoipinServices;



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


    @PostMapping("/listBattery")
    public List<Battery> listBattery(@RequestParam(value = "status") String status) {
        return dangkyDoipinServices.findStatus(status);
    }

    @PostMapping("/doiPin")
    public String existsByIdAndStatusRented(@RequestParam("battery_out_id") Integer battery_out_id,
                                            @RequestParam("battery_in_id") Integer battery_in_id) {

        // battery_out_id là PIN của trạm còn trống
        // battery_in_id là PIN của người dùng đang cho thuê

        // kiểm tra xem PIN cho thuê của người dùng có nằm trong database không và kiểm tra xem PIN người dùng chọn hiện có trống không
        // thỏa mãn điều kiện khi : battery_out_id còn trống và battery_in_id đang cho thuê thì sẽ tiến hành
        // battery_in_id đang vào tình trạng sạc, còn battery_out_id đưa vào tình trạng cho thuê
        if (dangkyDoipinServices.existsByIdAndStatusRented(battery_in_id) && dangkyDoipinServices.existsByIdAndStatusEmpty(battery_out_id)) {
            dangkyDoipinServices.updateStatusById(battery_in_id, "CHARGING");
            dangkyDoipinServices.updateStatusById(battery_in_id, "RENTED");
            return "Hợp Lý";
        } else {
            return "Không Hợp Lý";
        }
        // return dangkyDoipinServices.existsByIdAndStatusRented(id);
    }


}
