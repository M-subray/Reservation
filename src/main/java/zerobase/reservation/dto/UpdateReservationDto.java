package zerobase.reservation.dto;

import lombok.Getter;
import lombok.Setter;
import zerobase.reservation.type.ReservationStatus;


@Getter
@Setter
public class UpdateReservationDto {
    private String username;
    private ReservationStatus status;
}
