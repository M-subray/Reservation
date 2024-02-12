package zerobase.reservation.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservationStatus {
    WAITING("예약 대기 중"),
    CONFIRMED("예약 확정"),
    ;

    private final String description;
    public static final ReservationStatus DEFAULT = WAITING;
}