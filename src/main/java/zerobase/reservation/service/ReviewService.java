package zerobase.reservation.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.reservation.domain.Reservation;
import zerobase.reservation.domain.Review;
import zerobase.reservation.domain.Store;
import zerobase.reservation.dto.ReviewUpdateDto;
import zerobase.reservation.dto.ReviewWriteDto;
import zerobase.reservation.exception.ReviewException;
import zerobase.reservation.repository.ReservationRepository;
import zerobase.reservation.repository.ReviewRepository;
import zerobase.reservation.repository.StoreRepository;
import zerobase.reservation.type.ErrorCode;
import zerobase.reservation.type.ReservationStatus;
import zerobase.reservation.utils.ReservationDataAccessUtils;
import zerobase.reservation.utils.ReviewDataAccessUtils;
import zerobase.reservation.utils.StoreDataAccessUtils;
import zerobase.reservation.utils.UserAuthenticationUtils;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;

    // 리뷰 작성
    public void reviewWrite(ReviewWriteDto reviewWriteDto) {
        // userDetails 얻어오기 (로그인이 필요하거나 재로그인이 필요한 경우 에러 발생)
        UserDetails userDetails = UserAuthenticationUtils.findUserDetails();

        // fetchValidReservation 메서드를 통해 발생 가능 Exception 검증 후 Reservation 가져오기
        Reservation reservation = fetchValidReservation(reviewWriteDto, userDetails);

        reviewRepository.save(Review.builder()
                .storeName(reservation.getStoreName())
                .customerUsername(reservation.getUsername())
                .visitedDateTime(reservation.getRequestedTime())
                .review(reviewWriteDto.getReview())
                .build());
    }

    // 리뷰 작성 불가한 경우 걸러내기
    private Reservation fetchValidReservation(ReviewWriteDto reviewWriteDto, UserDetails userDetails) {

        // 해당 유저의 리뷰 내역이 없을 경우 에러 발생
        ReservationDataAccessUtils reservationDataAccessUtils =
                new ReservationDataAccessUtils(reservationRepository);
        Reservation reservation = reservationDataAccessUtils
                .byStoreNameAndUsernameForReservation(reviewWriteDto.getStoreName(), userDetails.getUsername());

        if (reservation.getStatus() == ReservationStatus.WAITING) {
            throw new RuntimeException("예약이 확정 되지 않았습니다.");
        }

        if (!reservation.isVisited()) {
            throw new RuntimeException("예약 내역은 존재하나, 매장을 이용하지 않은 고객입니다.");
        }

        if (reservation.getRequestedTime().isAfter(LocalDateTime.now())) {
            throw new RuntimeException("리뷰는 예약 시간 이후 부터 작성 가능 합니다.");
        }

        return reservation;
    }


    // 리뷰 수정
    public void reviewUpdate(Long reviewId, ReviewUpdateDto reviewUpdateDto) {
        // userDetails 얻어오기 (로그인이 필요하거나 재로그인이 필요한 경우 에러 발생)
        UserDetails userDetails = UserAuthenticationUtils.findUserDetails();

        // 해당 리뷰가 없을 경우 에러 발생
        ReviewDataAccessUtils reviewDataAccessUtils = new ReviewDataAccessUtils(reviewRepository);
        Review review = reviewDataAccessUtils.byIdForReview(reviewId);

        // 현재 로그인 된 계정과 관련 되지 않은 리뷰일 경우
        if (!userDetails.getUsername().equals(review.getCustomerUsername())) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        review.setReview(reviewUpdateDto.getNewReview());
        reviewRepository.save(review);
    }

    // 리뷰 삭제 (CUSTOMER 권한)
    public void reviewDeleteForCustomer(Long reviewId) {
        // userDetails 얻어오기 (로그인이 필요하거나 재로그인이 필요한 경우 에러 발생)
        UserDetails userDetails = UserAuthenticationUtils.findUserDetails();

        // 해당 리뷰가 없을 경우 에러 발생
        ReviewDataAccessUtils reviewDataAccessUtils = new ReviewDataAccessUtils(reviewRepository);
        Review review = reviewDataAccessUtils.byIdForReview(reviewId);

        // 현재 로그인 된 계정과 관련 되지 않은 리뷰일 경우
        if (!userDetails.getUsername().equals(review.getCustomerUsername())) {
            throw new ReviewException(ErrorCode.NOT_EXIST_AUTHORITY);
        }
        reviewRepository.delete(review);
    }

    // 리뷰 삭제 (PARTNER 권한)
    public void reviewDeleteForPartner(Long reviewId) {
        // userDetails 얻어오기 (로그인이 필요하거나 재로그인이 필요한 경우 에러 발생)
        UserDetails userDetails = UserAuthenticationUtils.findUserDetails();

        // 해당 리뷰가 없을 경우 에러 발생
        ReviewDataAccessUtils reviewDataAccessUtils = new ReviewDataAccessUtils(reviewRepository);
        Review review = reviewDataAccessUtils.byIdForReview(reviewId);

        // 해당 매장이 존재하지 않을경우 에러 발생
        StoreDataAccessUtils storeDataAccessUtils = new StoreDataAccessUtils(storeRepository);
        Store store = storeDataAccessUtils.byStoreNameForStore(review.getStoreName());

        // 현재 로그인 된 계정과 관련 되지 않은 리뷰일 경우
        if (!userDetails.getUsername().equals(store.getAccountUsername())) {
            throw new ReviewException(ErrorCode.NOT_EXIST_AUTHORITY);
        }
        reviewRepository.delete(review);
    }
}
