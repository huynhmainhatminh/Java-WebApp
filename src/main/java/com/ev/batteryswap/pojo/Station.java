package com.ev.batteryswap.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "stations")
@EntityListeners(AuditingEntityListener.class) // Thêm dòng này
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "location_lat", precision = 12, scale = 8)
    private BigDecimal locationLat;

    @Column(name = "location_lng", precision = 12, scale = 8)
    private BigDecimal locationLng;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @CreatedDate // Thay đổi
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @LastModifiedDate // Thay đổi
    @Column(name = "updated_at")
    private Instant updatedAt;
}