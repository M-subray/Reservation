package zerobase.reservation.exception;


import lombok.*;
import zerobase.reservation.type.ErrorCode;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class StoreException extends RuntimeException {
    private ErrorCode errorCode;
    private String errorMessage;

    public StoreException (ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }
}
