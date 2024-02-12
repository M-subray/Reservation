package zerobase.reservation.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.reservation.domain.Reservation;
import zerobase.reservation.domain.Store;
import zerobase.reservation.dto.UpdateReservationDto;
import zerobase.reservation.exception.ReservationException;
import zerobase.reservation.exception.StoreException;
import zerobase.reservation.repository.ReservationRepository;
import zerobase.reservation.repository.StoreRepository;
import zerobase.reservation.type.ErrorCode;
import zerobase.reservation.utils.ReservationDataAccessUtils;
import zerobase.reservation.utils.StoreDataAccessUtils;
import zerobase.reservation.utils.UserAuthenticationUtils;


@RequiredArgsConstructor
@Transactional
@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;

    // 매장 생성
    public void createReservation(Reservation reservation) {

        // 해당 매장이 존재하지 않을경우 에러 발생
        StoreDataAccessUtils storeDataAccessUtils =
                new StoreDataAccessUtils(storeRepository);
        Store store = storeDataAccessUtils
                .byStoreNameForStore(reservation.getStoreName());

        // 해당 매장이 존재하나, 예약이 불가한 경우(매장의 예약 상태가 아직 '예약 불가능' 인 경우)
        if (!store.isReservationAvailable()) {
            throw new ReservationException(ErrorCode.DISABLED_RESERVATION);
        }
        reservationRepository.save(reservation);
    }

    // 매장 수정
    public void updateReservation(String storeName,
                                            UpdateReservationDto updateReservationDto) {

        // userDetails 얻어오기 (로그인이 필요하거나 재로그인이 필요한 경우 에러 발생)
        UserDetails userDetails =
                UserAuthenticationUtils.findUserDetails();

        // storeName 을 통해 자신의 매장 가져오기(매장명이 존재하지 않는다면 에러발생)
        StoreDataAccessUtils storeDataAccessUtils =
                new StoreDataAccessUtils(storeRepository);
        Store store = storeDataAccessUtils.byStoreNameForStore(storeName);

        if (!userDetails.getUsername().equals(store.getAccountUsername())) {
            throw new StoreException(ErrorCode.NOT_OWNED_STORE);
        }

        // 조회하려는 storeName 과 username 을 통해 해당 예약 내역이 없다면 에러 발생
        ReservationDataAccessUtils reservationDataAccessUtils =
                new ReservationDataAccessUtils(reservationRepository);
        Reservation reservation = reservationDataAccessUtils
                .byStoreNameAndUsernameForReservation(storeName
                        , updateReservationDto.getUsername());

        reservation.setStatus(updateReservationDto.getStatus());
        reservationRepository.save(reservation);
    }
}
