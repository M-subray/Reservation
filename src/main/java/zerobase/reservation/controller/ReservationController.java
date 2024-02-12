package zerobase.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import zerobase.reservation.domain.Reservation;
import zerobase.reservation.domain.Store;
import zerobase.reservation.dto.CustomerReservationCheckDto;
import zerobase.reservation.dto.ReservationDto;
import zerobase.reservation.dto.UpdateReservationDto;
import zerobase.reservation.exception.GlobalExceptionHandler;
import zerobase.reservation.repository.ReservationRepository;
import zerobase.reservation.repository.StoreRepository;
import zerobase.reservation.service.ReservationService;
import zerobase.reservation.utils.ReservationDataAccessUtils;
import zerobase.reservation.utils.StoreDataAccessUtils;
import zerobase.reservation.utils.UserAuthenticationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class ReservationController {
    final private StoreRepository storeRepository;
    final private ReservationRepository reservationRepository;
    final private ReservationService reservationService;

    // CUSTOMER 권한으로 매장 예약 진행하기
    @PostMapping("/stores/reservation")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> createReservation (@RequestBody Reservation reservation) {
        try {
            reservationService.createReservation(reservation);
            return ResponseEntity.ok("예약이 완료 되었습니다. (매장으로 부터 예약 확정 이후 이용 가능)");
        } catch (Exception e) { // 예외 처리기로 예외 보내기
            return GlobalExceptionHandler.handleException(e);
        }
    }

    // CUSTOMER 로그인 id와 핸드폰 번호로 예약 내역 조회하기
    @GetMapping("/stores/reservation/details/customer")
    public ResponseEntity<?> responseDetailsForCustomer(
            @RequestBody CustomerReservationCheckDto customerReservationCheckDto) {

        String userName = customerReservationCheckDto.getUserName();
        String mobile = customerReservationCheckDto.getMobile();

        // 예약 계정이 아닐 경우 해당 계정의 예약 내역 없음 에러 발생
        ReservationDataAccessUtils reservationDataAccessUtils =
                new ReservationDataAccessUtils(reservationRepository);
        Reservation reservation = reservationDataAccessUtils.byUsernameForReservation(userName);

        // 예약 할 때 계정의 핸드폰 번호가 일치하지 않을 경우 에러 발생
        if (!mobile.equals(reservation.getMobile())) {
            return ResponseEntity
                    .badRequest()
                    .body("예약자 계정의 핸드폰 번호가 일치하지 않습니다.");
        }

        return ResponseEntity.ok(ReservationDto.fromEntity(reservation));
    }

    // PARTNER 권한으로 자신의 매장에 예약 된 내역 조회하기
    @GetMapping("/stores/reservation/details/partner")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> responseDetailsForPartner() {
        // userDetails 얻어오기 (로그인이 필요하거나 재로그인이 필요한 경우 에러 발생)
        UserDetails userDetails = UserAuthenticationUtils.findUserDetails();

        // userDetails 를 통해 등록 된 계정으로 (username)자신의 매장 가져오기(매장이 없다면 에러 발생)
        StoreDataAccessUtils storeDataAccessUtils = new StoreDataAccessUtils(storeRepository);
        Store store = storeDataAccessUtils.byAccountUsernameForStore(userDetails.getUsername());

        /*
        Optional<Store> 를 통해 예약 조회하려는 매장명을 가져와 Optional<List<Reservation>> 만들기
        (조회 내역이 한개 이상일 경우를 위해 List 로 가져오기 위함)
         */
        Optional<List<Reservation>> reservationsOptional =
                reservationRepository.findByStoreName(store.getStoreName());

        if (reservationsOptional.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("현재 매장에 대한 예약 내역이 없습니다.");
        }

        List<Reservation> reservations = reservationsOptional.get();
        List<ReservationDto> reservationDtos = new ArrayList<>();

        for (Reservation reservation : reservations) {
            reservationDtos.add(ReservationDto.fromEntity(reservation));
        }

        return ResponseEntity.ok(reservationDtos);
    }


    // PARTNER 권한으로 자신의 매장 예약 내역 수정하기(예약 확정 짓기)
    @PreAuthorize("hasRole('PARTNER')")
    @PutMapping("/stores/reservation/details/partner/{storeName}")
    public ResponseEntity<?> updateReservation (
            @PathVariable String storeName,
            @RequestBody UpdateReservationDto updateReservationDto) {

        try {
            reservationService.updateReservation(storeName, updateReservationDto);
            return ResponseEntity.ok("예약이 업데이트 되었습니다.");
        } catch (Exception e) { // 예외 처리기로 예외 보내기
            return GlobalExceptionHandler.handleException(e);
        }
    }
}
