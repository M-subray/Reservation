package zerobase.reservation.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import zerobase.reservation.exception.SigninException;
import zerobase.reservation.type.ErrorCode;

public class UserAuthenticationUtils {

    // 로그인 시간(10분)이 지났을 경우를 대비해 재로그인 안내를 위한 메서드
    public static UserDetails findUserDetails() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.isAuthenticated()) {
            throw new SigninException(ErrorCode.SIGNIN_TIME_OUT);
        }

        return (UserDetails) authentication.getPrincipal();
    }
}