package com.ev.batteryswap.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    private String password;

    @ColumnDefault("0.00")
    @Column(name = "wallet_balance", precision = 12, scale = 2)
    private BigDecimal walletBalance;

    @ColumnDefault("'DRIVER'")
    @Column(name = "role", nullable = false, length = 20)
    private String role;

    @ColumnDefault("'ACTIVE'")
    @Column(name = "status", length = 20)
    private String status;

    @CreatedDate // <<< THAY ĐỔI
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @LastModifiedDate // <<< THAY ĐỔI
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "username", nullable = false)
    private String username;

}