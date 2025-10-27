package com.restapi.api.repositories;

import com.restapi.api.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IGetInformation extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
