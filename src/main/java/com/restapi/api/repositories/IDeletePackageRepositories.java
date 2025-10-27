package com.restapi.api.repositories;

import com.restapi.api.pojo.RentalPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IDeletePackageRepositories extends JpaRepository<RentalPackage, Integer> {

    boolean existsByUser_id(Integer userId);

    @Transactional
    @Modifying
    void deleteByUser_id(Integer userId);

}
