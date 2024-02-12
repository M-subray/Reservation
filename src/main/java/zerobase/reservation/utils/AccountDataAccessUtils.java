package zerobase.reservation.utils;

import lombok.RequiredArgsConstructor;
import zerobase.reservation.domain.Account;
import zerobase.reservation.exception.StoreException;
import zerobase.reservation.repository.AccountRepository;
import zerobase.reservation.type.ErrorCode;

import java.util.Optional;

@RequiredArgsConstructor
public class AccountDataAccessUtils {
    private final AccountRepository accountRepository;

    public String findByUsernameForAccount (String username) {
        Optional<Account> optionalAccount =
                accountRepository.findByUsername(username);

        Account existingAccount = optionalAccount.orElseThrow(() ->
                new StoreException(ErrorCode.USER_NOT_FOUND));

        return existingAccount.getUsername();
    }
}
