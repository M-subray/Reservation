package zerobase.reservation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zerobase.reservation.domain.Account;
import zerobase.reservation.service.SignupService;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SignupController {
    private final SignupService signupService;
    private final PasswordEncoder passwordEncoder;

    // CUSTOMER 또는 PARTNER 권한을 갖는 계정 가입하기
    @PostMapping("/signup")
    public ResponseEntity<Void> joinAccount(@RequestBody Account account) {
        signupService.signup(account, passwordEncoder);
        return ResponseEntity.ok().build();
    }
}
