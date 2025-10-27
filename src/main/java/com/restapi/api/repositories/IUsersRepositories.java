package com.restapi.api.repositories;

import com.restapi.api.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface IUsersRepositories extends JpaRepository<User, Integer> {


    public User findByEmail(String email);
    public User findByRole(String role);
    public User findByFullName(String fullName);

    @Transactional
    @Modifying
    void deleteByUsername(String username);

//    public User deleteByUsername(String username);
//    public User deleteByEmail(String email);

    // public User updateByUsername(String username);


}
