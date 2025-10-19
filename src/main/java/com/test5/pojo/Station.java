package com.test5.pojo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "stations")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "station_id", nullable = false)
    private Integer id;

    @Column(name = "station_name", nullable = false, length = 100)
    private String stationName;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "contact_number", length = 20)
    private String contactNumber;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

}