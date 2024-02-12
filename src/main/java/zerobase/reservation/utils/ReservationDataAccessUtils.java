package zerobase.reservation.utils;

import lombok.RequiredArgsConstructor;
import zerobase.reservation.domain.Reservation;
import zerobase.reservation.exception.ReservationException;
import zerobase.reservation.repository.ReservationRepository;
import zerobase.reservation.type.ErrorCode;

import java.util.Optional;

@RequiredArgsConstructor
public class ReservationDataAccessUtils {
    private final ReservationRepository reservationRepository;

    public Reservation byStoreNameAndUsernameForReservation (String storeName, String username) {
        Optional<Reservation> byStoreNameAndUsername =
                reservationRepository.findByStoreNameAndUsername(storeName, username);

        byStoreNameAndUsername.orElseThrow(() ->
                new ReservationException(ErrorCode.RESERVATION_NOT_FOUND));

        return byStoreNameAndUsername.get();
    }

    public Reservation byUsernameForReservation (String username) {
        Optional<Reservation> byUsername =
                reservationRepository.findByUsername(username);

        byUsername.orElseThrow(() ->
                new ReservationException(ErrorCode.RESERVATION_NOT_FOUND));

        return byUsername.get();
    }
}
