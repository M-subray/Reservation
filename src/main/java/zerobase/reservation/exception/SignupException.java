package zerobase.reservation.exception;

import lombok.*;
import zerobase.reservation.type.ErrorCode;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupException extends RuntimeException {
    private ErrorCode errorCode;
    private String errorMessage;

    public SignupException (ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }
}
