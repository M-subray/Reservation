package zerobase.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.reservation.domain.Account;
import zerobase.reservation.exception.SigninException;
import zerobase.reservation.exception.SignupException;
import zerobase.reservation.repository.AccountRepository;
import zerobase.reservation.type.ErrorCode;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final AccountRepository accountRepository;

    @Transactional
    public void signup(Account account, PasswordEncoder passwordEncoder) {
        boolean availableId = checkId(account.getUsername());
        if (!availableId) {
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            accountRepository.save(account);
        } else {
            throw new SignupException(ErrorCode.USER_ALREADY_EXISTS);
        }
    }

    // 해당 아이디가 이미 존재한다면 false, 없다면 true
    public boolean checkId(String username) {
        Optional<Account> existingAccount = accountRepository.findByUsername(username);
        return existingAccount.isPresent();
    }
}