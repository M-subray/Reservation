//package zerobase.reservation.service;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import zerobase.reservation.domain.Account;
//import zerobase.reservation.repository.AccountRepository;
//import zerobase.reservation.type.Authority;
//
//import java.time.LocalDateTime;
//
//import static org.mockito.BDDMockito.given;
//
//@ExtendWith(MockitoExtension.class)
//class JoinServiceTest {
//    PasswordEncoder passwordEncoder;
//    @Mock
//    private AccountRepository accountRepository;
//
//    @InjectMocks
//    private JoinService joinService;
//
//    @Test
//    void createAccountTest() {
//        Account account;
//        //given
//        given(account = Account.builder()
//                .loginId("subray1")
//                .password(passwordEncoder.encode("123456"))
//                .userName("Moon")
//                .mobile("010-1234-5678")
//                .registeredAt(LocalDateTime.now())
//                .userRole(Authority.PARTNER)
//                .build());
//        //when
//        //then
//    }
//
//
//}