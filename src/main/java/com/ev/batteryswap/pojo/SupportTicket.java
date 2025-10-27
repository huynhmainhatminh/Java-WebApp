package com.ev.batteryswap.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "support_tickets")
@EntityListeners(AuditingEntityListener.class)
public class SupportTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "ticket_number", nullable = false, unique = true, length = 20)
    private String ticketNumber;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Lob
    @Column(name = "message", nullable = false)
    private String message;

    @ColumnDefault("'OPEN'")
    @Column(name = "status", nullable = false, length = 50) // Bỏ @Lob, dùng Column thường
    private String status;

    @ColumnDefault("'MEDIUM'")
    @Column(name = "priority", nullable = false, length = 50) // Bỏ @Lob, dùng Column thường
    private String priority;

    @Lob
    @Column(name = "admin_response")
    private String adminResponse;

    @CreatedDate // Thay đổi
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @LastModifiedDate // Thay đổi
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "resolved_at")
    private Instant resolvedAt;
}