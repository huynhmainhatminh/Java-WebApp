package com.ev.batteryswap.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "batteries")
@EntityListeners(AuditingEntityListener.class)
public class Battery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_station_id")
    private Station station;

    @Column(name = "serial_number", nullable = false, unique = true, length = 100)
    private String nameBattery; // Giữ lại nameBattery để tương thích DTO và service

    @Column(name = "model", nullable = false, length = 100)
    private String model;

    @Column(name = "capacity_kwh", nullable = false, precision = 8, scale = 2)
    private BigDecimal capacityKwh;

    @Column(name = "current_charge_percentage", nullable = false, precision = 5, scale = 2)
    private BigDecimal currentChargePercentage;

    @Column(name = "health_percentage", nullable = false, precision = 5, scale = 2)
    private BigDecimal soh; // Giữ lại soh để tương thích DTO và service

    @Column(name = "charge_cycles", nullable = false)
    private Integer chargeCycles = 0;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Temporal(TemporalType.DATE)
    @Column(name = "manufacture_date")
    private Date manufactureDate;

    @CreatedDate // <<< THAY ĐỔI
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @LastModifiedDate // <<< THAY ĐỔI
    @Column(name = "updated_at")
    private Instant updatedAt;
}