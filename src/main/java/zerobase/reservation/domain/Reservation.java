package zerobase.reservation.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import zerobase.reservation.type.ReservationStatus;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String storeName;

    @Column(name = "account_username")
    private String username;

    @Column(name = "account_name")
    private String name;

    @Column(name = "account_mobile")
    private String mobile;

    @Column(name = "requested_time")
    private LocalDateTime requestedTime;

    private LocalDateTime registeredAt;

    @Column(name = "reservationStatus")
    @Enumerated(EnumType.STRING)
    private ReservationStatus status = ReservationStatus.DEFAULT;

    @Column(columnDefinition = "boolean default false")
    private boolean visited;

    @PrePersist
    public void setRegisteredAt() {
        this.registeredAt = LocalDateTime.now();
    }
}
