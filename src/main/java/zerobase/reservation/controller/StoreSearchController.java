package zerobase.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import zerobase.reservation.dto.StoreSearchDto;
import zerobase.reservation.exception.StoreException;
import zerobase.reservation.service.StoreService;
import zerobase.reservation.type.ErrorCode;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class StoreSearchController {

    private final StoreService storeService;

    // 전체 매장 조회하기
    @GetMapping("/stores/search")
    public ResponseEntity<?> searchStore(final Pageable pageable) {
        Page<String> storeNames = this.storeService.getAllStoreNames(pageable);
        return ResponseEntity.ok(storeNames);
    }

    // 특정 매장 상세정보 조회하기
    @GetMapping("/stores/search/{storeName}")
    public ResponseEntity<?> storeDetail(@PathVariable String storeName) {
        Optional<StoreSearchDto> store = storeService.getStoreByStoreName(storeName);

        // 상제정보 조회하려는 매장이 존재하지 않으면 에러 발생
        store.orElseThrow(() ->
                new StoreException(ErrorCode.STORE_NOT_FOUND));

        StoreSearchDto storeEntity = store.get();
        Map<String, Object> response = new HashMap<>();
        response.put("storeName", storeEntity.getStoreName());
        response.put("storeAddress", storeEntity.getStoreAddress());
        response.put("storeInformation", storeEntity.getStoreInformation());
        response.put("reservationStatus", storeEntity.getReservationStatus());
        return ResponseEntity.ok(response);
    }
}
