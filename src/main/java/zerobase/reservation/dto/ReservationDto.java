package zerobase.reservation.dto;


import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import zerobase.reservation.domain.Reservation;
import zerobase.reservation.type.ReservationStatus;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
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

    @Enumerated(EnumType.STRING)
    @Column(name = "reservationStatus")
    private String status;

    private String visited;

    public static ReservationDto fromEntity(Reservation reservation) {

        String visited = reservation.isVisited() ? "매장 방문 완료" : "매장 방문 전";

        return ReservationDto.builder()
                .storeName(reservation.getStoreName())
                .username(reservation.getUsername())
                .name(reservation.getName())
                .mobile(reservation.getMobile())
                .requestedTime(reservation.getRequestedTime())
                .registeredAt(reservation.getRegisteredAt())
                .status(reservation.getStatus().getDescription())
                .visited(visited)
                .build();
    }
}
