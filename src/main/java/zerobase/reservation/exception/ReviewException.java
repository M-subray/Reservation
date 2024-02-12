package zerobase.reservation.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.reservation.type.ErrorCode;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ReviewException extends RuntimeException {
    private ErrorCode errorCode;
    private String errorMessage;

    public ReviewException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }
}
