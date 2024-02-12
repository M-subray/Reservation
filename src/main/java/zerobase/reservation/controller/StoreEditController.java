package zerobase.reservation.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zerobase.reservation.domain.Store;
import zerobase.reservation.dto.StoreDto;
import zerobase.reservation.exception.StoreException;
import zerobase.reservation.repository.StoreRepository;
import zerobase.reservation.service.StoreService;
import zerobase.reservation.type.ErrorCode;
import zerobase.reservation.utils.StoreDataAccessUtils;
import zerobase.reservation.utils.StoreSearchDtoDataAccessUtils;
import zerobase.reservation.utils.UserAuthenticationUtils;


@RequiredArgsConstructor
@RestController
public class StoreEditController {

    private final StoreService storeService;
    private final StoreRepository storeRepository;


    // PARTNER 권한으로 매장 등록하기
    @PostMapping("/stores/register")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> storeRegistration(
            @RequestBody @Validated(StoreDto.GroupOrder.class)
            StoreDto storeDto, BindingResult bindingResult) {

        // userDetails 얻어오기 (로그인이 필요하거나 재로그인이 필요한 경우 에러 발생)
        UserDetails userDetails = UserAuthenticationUtils.findUserDetails();

        // 현재 로그인 계정의 username 을 AccountUsername 으로 등록해준다.
        storeDto.setAccountUsername(userDetails.getUsername());

        // DB에 생성하려는 매장명이 존재 한다면 에러 발생(매장명 중복 불가)
        StoreSearchDtoDataAccessUtils storeSearchDtoDataAccessUtils =
                new StoreSearchDtoDataAccessUtils(storeService);
        storeSearchDtoDataAccessUtils.byStoreNameForStoreSearchDto(storeDto);

        // Validated 에서 에러가 있었을 경우 BindingResult 처리
        if (bindingResult.hasErrors()) {
            // 개별 에러 처리를 위해 그룹으로 지정해 놓은 storeName 에 관련 된 에러 처리
            if (bindingResult.hasFieldErrors("storeName")) {
                throw new StoreException(ErrorCode.WRONG_STORE_NAME);
            }

            // 개별 에러 처리를 위해 그룹으로 지정해 놓은 storeAddress 에 관련 된 에러 처리
            if (bindingResult.hasFieldErrors("storeAddress")) {
                throw new StoreException(ErrorCode.WRONG_STORE_ADDRESS);
            }
        }

        /*
        위에서 검증 된 데이터를 StoreDto 의 fromEntity 에 객체로 넘겨
        새 Store 객체를 만들어 내 storeService 의 storeRegistration 메서드를 통해
        StoreRepository 로 넘기게 된다.
         */
        storeService.storeRegistration(StoreDto.fromEntity(storeDto));

        return ResponseEntity.ok(StoreDto.fromEntity(storeDto));
    }

    // 해당 매장의 권한을 갖는 PARTNER 권한으로 매장 삭제하기
    @DeleteMapping("/stores/delete/{storeName}")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<String> deleteStore(@PathVariable String storeName) {

        // userDetails 얻어오기 (로그인이 필요하거나 재로그인이 필요한 경우 에러 발생)
        UserDetails userDetails = UserAuthenticationUtils.findUserDetails();

        // 해당 매장이 존재하지 않을경우 에러 발생
        StoreDataAccessUtils storeDataAccessUtils =
                new StoreDataAccessUtils(storeRepository);
        Store store = storeDataAccessUtils
                .byStoreNameForStore(storeName);

        // 로그인 된 계정에 등록 된 매장명과 삭제 하려는 매장명이 다를 경우 에러 발생 (타매장 삭제 권한 없음)
        String culUsername = userDetails.getUsername();
        if (!culUsername.equals(store.getAccountUsername())) {
            throw new StoreException(ErrorCode.NOT_EXIST_AUTHORITY);
        }

        storeRepository.delete(store);
        return ResponseEntity.ok(String.format("매장명 : (%s)은 매장 목록에서 제외 되었습니다."
                , store.getStoreName()));
    }

    // 매장 정보 수정 (예약 가능 여부 상태 수정)
    @PutMapping("/stores/update/{storeName}")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> updateStore(@PathVariable String storeName,
                                         @RequestBody @Validated(StoreDto.GroupOrder.class)
                                         StoreDto storeDto,
                                         BindingResult bindingResult) {

        // userDetails 얻어오기 (로그인이 필요하거나 재로그인이 필요한 경우 에러 발생)
        UserDetails userDetails = UserAuthenticationUtils.findUserDetails();

        // 해당 매장이 존재하지 않을경우 에러 발생
        StoreDataAccessUtils storeDataAccessUtils =
                new StoreDataAccessUtils(storeRepository);

        Store store = storeDataAccessUtils
                .byStoreNameForStore(storeName);

        // 로그인 된 계정에 등록 된 매장명과 수정 하려는 매장명이 다를 경우 에러 발생 (타매장 수정 권한 없음)
        String culUsername = userDetails.getUsername();
        if (!culUsername.equals(store.getAccountUsername())) {
            throw new StoreException(ErrorCode.NOT_EXIST_AUTHORITY);
        }

        // Validated 에서 에러가 있었을 경우 BindingResult 처리
        if (bindingResult.hasErrors()) {

            // 개별 에러 처리를 위해 그룹으로 지정해 놓은 storeName 에 관련 된 에러 처리
            if (bindingResult.hasFieldErrors("storeName")) {
                throw new StoreException(ErrorCode.WRONG_STORE_NAME);
            }

            // 개별 에러 처리를 위해 그룹으로 지정해 놓은 storeAddress 에 관련 된 에러 처리
            if (bindingResult.hasFieldErrors("storeAddress")) {
                throw new StoreException(ErrorCode.WRONG_STORE_ADDRESS);
            }
        }

        // 검증 마친 데이터로 수정 진행
        storeService.updateStore(store, StoreDto.fromEntity(storeDto));
        return ResponseEntity.ok(StoreDto.fromEntity(storeDto));
    }
}
