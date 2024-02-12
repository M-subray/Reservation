package zerobase.reservation.exception;


import lombok.*;
import zerobase.reservation.type.ErrorCode;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SigninException extends RuntimeException{
    private ErrorCode errorCode;
    private String errorMessage;

    public SigninException (ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = getErrorCode();
    }
}
