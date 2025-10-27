package com.restapi.api.services.interfaces;

public interface IDeletePackageServices {

    boolean existsByUser_id(Integer userId);
    void deleteByUser_id(Integer userId);

}
