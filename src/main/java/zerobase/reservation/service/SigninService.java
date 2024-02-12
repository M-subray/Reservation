package zerobase.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.reservation.domain.Account;
import zerobase.reservation.exception.SigninException;
import zerobase.reservation.repository.AccountRepository;
import zerobase.reservation.type.ErrorCode;


@Service
@RequiredArgsConstructor
public class SigninService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    @Transactional
    public Account signin(String username, String password) {
        Account savedAccount = accountRepository.findByUsername(username)
                .orElseThrow(() -> new SigninException(ErrorCode.USER_NOT_FOUND));
        boolean passwordIsMatch = this.passwordEncoder.matches(password
                , savedAccount.getPassword());
        if (passwordIsMatch) {
            return savedAccount;
        } else {
            throw new SigninException(ErrorCode.WRONG_PASSWORD);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.accountRepository.findByUsername(username)
                .orElseThrow(() -> new SigninException(ErrorCode.USER_NOT_FOUND));
    }
}
