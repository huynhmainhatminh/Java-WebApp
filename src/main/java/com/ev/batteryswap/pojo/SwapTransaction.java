package com.ev.batteryswap.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "swap_transactions")
@EntityListeners(AuditingEntityListener.class)
public class SwapTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    // THÊM TRƯỜNG user VÀO ĐÂY
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // Giả sử cột khóa ngoại trong DB là user_id
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "station_id", nullable = false)
    private Station station;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "battery_out_id", nullable = false)
    private Battery batteryOut;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "battery_in_id", nullable = false)
    private Battery batteryIn;

    // ... các trường còn lại giữ nguyên
    @ColumnDefault("0.00")
    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "payment_method", nullable = false, length = 10)
    private String paymentMethod;

    @Column(name = "payment_status", nullable = false, length = 20)
    private String paymentStatus;

    @Lob
    @Column(name = "notes")
    private String notes;

    @CreatedDate // Thay đổi
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;
}