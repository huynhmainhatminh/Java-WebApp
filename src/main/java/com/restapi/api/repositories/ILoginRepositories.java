package com.restapi.api.repositories;

import com.restapi.api.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILoginRepositories extends JpaRepository<User, Integer> {

    boolean existsByUsernameAndPassword(String username, String password);
}
