package zerobase.reservation.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zerobase.reservation.domain.Reservation;
import zerobase.reservation.exception.GlobalExceptionHandler;
import zerobase.reservation.repository.ReservationRepository;
import zerobase.reservation.type.ReservationStatus;
import zerobase.reservation.utils.ReservationDataAccessUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class StoreVisitedController {
    final private ReservationRepository reservationRepository;

    // 예약자 아이디와 모바일 번호로 방문 확인 하기
    @PutMapping("/stores/visited")
    public ResponseEntity<?> storeVisited(@RequestParam String storeName, String userName, String mobile) {

        try {
            // 예약 내역이 없을 경우 에러 발생
            ReservationDataAccessUtils reservationDataAccessUtils =
                    new ReservationDataAccessUtils(reservationRepository);
            Reservation reservation = reservationDataAccessUtils
                    .byStoreNameAndUsernameForReservation(storeName
                            , userName);

            if (reservation.getStatus() == ReservationStatus.WAITING) {
                throw new RuntimeException("예약이 확정 되지 않았습니다.");
            }

            if (!mobile.equals(reservation.getMobile())) {
                throw new RuntimeException("예약자명과 핸드폰 번호가 일치하지 않습니다.");
            }

            LocalDateTime localDateTime = LocalDateTime.now();
            LocalDateTime tenMinutesBefore = reservation.getRequestedTime().minusMinutes(10);

            if (localDateTime.isBefore(tenMinutesBefore)) {
                throw new RuntimeException("요청 주신 예약 시간 10분 이전 부터 방문 확인이 가능합니다.");
            }

            // 방문 여부를 true 로 설정
            reservation.setVisited(true);
            reservationRepository.save(reservation);

            return ResponseEntity.ok("방문이 확인되었습니다.");

        } catch (RuntimeException e) {
            return GlobalExceptionHandler.handleException(e);
        }
    }
}
