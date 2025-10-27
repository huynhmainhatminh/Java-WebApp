package com.restapi.api.repositories;

import com.restapi.api.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IGetInformation extends JpaRepository<User, Integer> {
    User findByUsername(String username);
<<<<<<< HEAD
    boolean existsByUsername(String username);

=======
>>>>>>> 4b548f1d7bc27aa26725a049737f7e08babeccc2
}
