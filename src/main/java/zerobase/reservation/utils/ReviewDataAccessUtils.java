package zerobase.reservation.utils;

import lombok.RequiredArgsConstructor;
import zerobase.reservation.domain.Review;
import zerobase.reservation.exception.ReviewException;
import zerobase.reservation.repository.ReviewRepository;
import zerobase.reservation.type.ErrorCode;

import java.util.Optional;

@RequiredArgsConstructor
public class ReviewDataAccessUtils {
    private final ReviewRepository reviewRepository;

    public Review byIdForReview (Long reviewId) {
        Optional<Review> byId =
                reviewRepository.findById(reviewId);

        byId.orElseThrow(() ->
                new ReviewException(ErrorCode.REVIEW_NOT_FOUND));

        return byId.get();
    }
}
