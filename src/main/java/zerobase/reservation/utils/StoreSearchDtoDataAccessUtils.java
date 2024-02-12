package zerobase.reservation.utils;

import lombok.RequiredArgsConstructor;
import zerobase.reservation.dto.StoreDto;
import zerobase.reservation.dto.StoreSearchDto;
import zerobase.reservation.exception.StoreException;
import zerobase.reservation.service.StoreService;
import zerobase.reservation.type.ErrorCode;

import java.util.Optional;

@RequiredArgsConstructor
public class StoreSearchDtoDataAccessUtils {
    private final StoreService storeService;

    // 해당 매장명을 DB 에 조회 후 같은 이름이 존재한다면 중복 생성 불가를 위한 에러 발생
    public void byStoreNameForStoreSearchDto(StoreDto storeDto) {
        Optional<StoreSearchDto> storeByStoreName = storeService.getStoreByStoreName(storeDto.getStoreName());

        if (storeByStoreName.isPresent()) {
            throw new StoreException(ErrorCode.ALREADY_STORE_NAME);
        }
    }
}