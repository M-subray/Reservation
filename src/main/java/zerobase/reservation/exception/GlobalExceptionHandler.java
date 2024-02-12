package zerobase.reservation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GlobalExceptionHandler {

    // 예외 처리기로 모든 예외를 한 곳에서 처리
    public static ResponseEntity<?> handleException (Exception e) {
        if (e instanceof StoreException) {
            return ResponseEntity.badRequest().body(((StoreException) e).getErrorCode().getDescription());
        } else if (e instanceof ReservationException) {
            return ResponseEntity.badRequest().body(((ReservationException) e).getErrorCode().getDescription());
        } else if (e instanceof SigninException) {
            return ResponseEntity.badRequest().body(((SigninException) e).getErrorCode().getDescription());
        } else if (e instanceof ReviewException) {
            return ResponseEntity.badRequest().body(((ReviewException) e).getErrorCode().getDescription());
        } else if (e instanceof RuntimeException) {
            return ResponseEntity.badRequest().body(((RuntimeException) e).getMessage());
        } else { // 예기치 못한 오류 발생의 경우
            return ResponseEntity.status(HttpStatus
                            .INTERNAL_SERVER_ERROR)
                    .body("서버 오류가 발생했습니다. 관리자에게 문의 바랍니다.");
        }
    }
}
