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

@Getter
@Setter
@Entity
@Table(name = "rentals")
@EntityListeners(AuditingEntityListener.class)
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "package_id", nullable = false)
    private RentalPackage packageField;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "battery_id", nullable = false)
    private Battery battery;

    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @Column(name = "end_date", nullable = false)
    private Instant endDate;

    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @ColumnDefault("'ACTIVE'")
    @Lob
    @Column(name = "status")
    private String status;


    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;


    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;

}