package com.ev.batteryswap.repositories;

import com.ev.batteryswap.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IUserRepositories extends JpaRepository<User, Integer> {

    boolean existsByUsernameAndPassword(String username, String password);
    boolean existsByUsername(String username);

    User findByUsername(String username);
    User findByRole(String role);
    User findById(int id);
}
