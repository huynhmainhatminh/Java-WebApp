package com.restapi.api.services;


import com.restapi.api.pojo.Battery;
import com.restapi.api.repositories.IDangkyDoipin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DangkyDoipinServices {

    @Autowired
    IDangkyDoipin idangkyDoipin;

    public List<Battery> findStatus(String status) {
        return idangkyDoipin.findByStatus(status);
    }


    public boolean existsByIdAndStatusRented(@Param("id") Integer id) {
        return idangkyDoipin.existsByIdAndStatusRented(id);
    }

    public boolean existsByIdAndStatusEmpty(@Param("id") Integer id) {
        return  idangkyDoipin.existsByIdAndStatusEmpty(id);
    }

    public void updateStatusById(@Param("id") Integer id, @Param("status") String status) {
        idangkyDoipin.updateStatusById(id, status);
    }

}
