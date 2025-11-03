package com.ev.batteryswap.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "batteries")
public class Battery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    // Thêm trường này để liên kết với Station
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private Station station;

    @Column(name = "serial_number", nullable = false, length = 100)
    private String serialNumber;

    @Column(name = "model", nullable = false, length = 100)
    private String model;

    @Column(name = "capacity_kwh", nullable = false, precision = 8, scale = 2)
    private BigDecimal capacityKwh;

    @ColumnDefault("0.00")
    @Column(name = "current_charge_percentage", precision = 5, scale = 2)
    private BigDecimal currentChargePercentage;

    @ColumnDefault("100.00")
    @Column(name = "health_percentage", precision = 5, scale = 2)
    private BigDecimal healthPercentage;

    @ColumnDefault("0")
    @Column(name = "charge_cycles")
    private Integer chargeCycles;

    @ColumnDefault("'EMPTY'")
    @Lob
    @Column(name = "status")
    private String status;

    @Column(name = "manufacture_date")
    private LocalDate manufactureDate;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;
}