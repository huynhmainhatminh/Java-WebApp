package com.restapi.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.restapi.api.pojo.User;

@Repository
public interface IRegisterRepositories extends JpaRepository<User, Integer> {

    boolean existsByUsername(String username);

}
