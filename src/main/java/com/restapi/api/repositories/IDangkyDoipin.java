package com.restapi.api.repositories;


import com.restapi.api.pojo.Battery;
import com.restapi.api.pojo.SwapTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository

public interface IDangkyDoipin extends JpaRepository<Battery, Integer> {

    List<Battery> findByStatus(String status);

    // tìm kiếm pin đang cho thuê
    @Query("SELECT COUNT(b) > 0 FROM Battery b WHERE b.id = :id AND b.status = 'RENTED'")
    boolean existsByIdAndStatusRented(@Param("id") Integer id);

    // tìm kiếm pin trống chưa cho thuê
    @Query("SELECT COUNT(b) > 0 FROM Battery b WHERE b.id = :id AND b.status = 'EMPTY'")
    boolean existsByIdAndStatusEmpty(@Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("UPDATE Battery b SET b.status = :status WHERE b.id = :id")
    void updateStatusById(@Param("id") Integer id, @Param("status") String status);

}
