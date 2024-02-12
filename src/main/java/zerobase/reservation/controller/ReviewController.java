package zerobase.reservation.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zerobase.reservation.dto.ReviewUpdateDto;
import zerobase.reservation.dto.ReviewWriteDto;
import zerobase.reservation.exception.GlobalExceptionHandler;
import zerobase.reservation.service.ReviewService;

@RequiredArgsConstructor
@RestController
public class ReviewController {
    private final ReviewService reviewService;

    // 매장 이용 후 리뷰 작성하기
    @PostMapping("/stores/review")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> reviewWrite (@RequestBody ReviewWriteDto reviewWriteDto) {
        try {
            reviewService.reviewWrite(reviewWriteDto);
            return ResponseEntity.ok("작성해 주셔서 감사합니다.");
        }
        catch (Exception e) {
            return GlobalExceptionHandler.handleException(e);
        }
    }

    // CUSTOMER 권한으로 자신의 리뷰 수정하기
    @PutMapping("/stores/review/update")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> reviewUpdate(
            @RequestParam Long reviewId,
            @RequestBody ReviewUpdateDto reviewUpdateDto) {
        try {
            reviewService.reviewUpdate(reviewId, reviewUpdateDto);
            return ResponseEntity.ok("수정이 완료 되었습니다.");
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e);
        }
    }


    // CUSTOMER 권한으로 자신의 리뷰 삭제하기
    @DeleteMapping("/stores/review/customer/delete")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> reviewDeleteForCustomer (@RequestParam Long reviewId) {
        try {
            reviewService.reviewDeleteForCustomer(reviewId);
            return ResponseEntity.ok("리뷰가 삭제 되었습니다.");
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e);
        }
    }

    // PARTNER 권한으로 자신의 매장 리뷰 삭제하기
    @DeleteMapping("/stores/review/partner/delete")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> reviewDeleteForPartner (@RequestParam Long reviewId) {
        try {
            reviewService.reviewDeleteForPartner(reviewId);
            return ResponseEntity.ok("리뷰가 삭제 되었습니다.");
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e);
        }
    }
}
