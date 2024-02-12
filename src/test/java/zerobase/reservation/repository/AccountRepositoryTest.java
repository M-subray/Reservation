//package zerobase.reservation.repository;
//
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//import zerobase.reservation.domain.Account;
//import zerobase.reservation.type.Authority;
//
//import java.time.LocalDateTime;
//
//import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
//
//
//@Transactional
//@SpringBootTest
//class AccountRepositoryTest {
//
//    @Autowired
//    AccountRepository accountRepository;
//
//    @Test
//    void AccountInsertTest() {
//        //given
//        Account account = new Account(1L, "subray", "461352"
//                , "moon", "010-4499-3345"
//                , LocalDateTime.now(), Authority.CUSTOMER);
//        //when
//        accountRepository.save(account);
//        //then
//        Account retrievedAccount = accountRepository.getById(1L);
//        assertNotNull(retrievedAccount);
//    }
//}