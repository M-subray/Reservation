package zerobase.reservation.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_ALREADY_EXISTS("해당 아이디는 이미 존재합니다."),
    USER_NOT_FOUND("해당 아이디는 존재하지 않습니다."),
    WRONG_PASSWORD("비밀번호가 일치하지 않습니다."),
    SIGNIN_TIME_OUT("재로그인이 필요 합니다."),

    WRONG_STORE_NAME("매장명이 유효하지 않습니다.(두글자 이상 서른글자 이하 지정 요망)"),
    ALREADY_STORE_NAME("이미 존재하는 매장명입니다. (중복 불가)"),
    STORE_NOT_FOUND("해당 매장은 존재하지 않습니다."),
    NOT_EXIST_AUTHORITY("권한이 없습니다."),
    WRONG_STORE_ADDRESS("주소가 유효하지 않습니다.(필수 입력)"),

    RESERVATION_NOT_FOUND("해당 유저의 예약 내역이 없습니다."),
    REVIEW_NOT_FOUND("해당 리뷰는 존재하지 않습니다."),
    DISABLED_RESERVATION("이 매장은 현재 예약이 비활성화되어 있습니다."),
    RESERVATION_ACCOUNT_MISMATCH("예약자 계정의 핸드폰 번호가 일치하지 않습니다."),
    NOT_OWNED_STORE("현재 계정의 소유 매장이 아닙니다.")
    ;

    private final String description;
}
