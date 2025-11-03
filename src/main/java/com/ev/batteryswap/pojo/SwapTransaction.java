package com.ev.batteryswap.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "swap_transactions")
public class SwapTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    // SỬA LẠI: Bỏ 'optional = false' và 'nullable = false'
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private Station station;

    // SỬA LẠI: Bỏ 'optional = false' và 'nullable = false'
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "battery_out_id")
    private Battery batteryOut;

    // SỬA LẠI: Bỏ 'optional = false' và 'nullable = false'
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "battery_in_id")
    private Battery batteryIn;

    @ColumnDefault("0.00")
    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Lob
    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Lob
    @Column(name = "payment_status", nullable = false)
    private String paymentStatus;

    @Lob
    @Column(name = "notes")
    private String notes;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

}