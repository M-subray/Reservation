package zerobase.reservation.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import zerobase.reservation.domain.Account;
import zerobase.reservation.repository.AccountRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SigninServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private SigninService signinService;

    @Test
    void test() {
        //given
        String username = "testUser";
        String password = "password";
        Account testAccount = new Account();
        testAccount.setUsername(username);
        testAccount.setPassword(passwordEncoder.encode(password));

        when(accountRepository.findByUsername(username)).thenReturn(Optional.of(testAccount));
        when(passwordEncoder.matches(password, testAccount.getPassword())).thenReturn(true);

        //when


        //then

    }
}

