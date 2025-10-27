package com.restapi.api.repositories;

import com.restapi.api.pojo.RentalPackage;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IRegisterPackage extends JpaRepository<RentalPackage, Integer> {

}
