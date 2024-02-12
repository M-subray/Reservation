package zerobase.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zerobase.reservation.config.TokenProvider;
import zerobase.reservation.domain.Account;
import zerobase.reservation.service.SigninService;

@RequiredArgsConstructor
@RestController
public class SigninController {
    private final SigninService signinService;
    private final TokenProvider tokenProvider;

    // 계정 로그인
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody Account account) {
        Account loginSuccessful = signinService.signin(account.getUsername(), account.getPassword());
        String token = this.tokenProvider.generateToken(loginSuccessful
                .getUsername(), String.valueOf(loginSuccessful.getRoles()));
        return ResponseEntity.ok(token);
    }
}
