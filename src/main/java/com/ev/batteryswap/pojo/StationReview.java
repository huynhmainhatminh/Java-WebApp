package com.ev.batteryswap.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "station_reviews")
@EntityListeners(AuditingEntityListener.class)
public class StationReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "rating", nullable = false)
    private Byte rating;

    @Lob
    @Column(name = "comment")
    private String comment;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

}