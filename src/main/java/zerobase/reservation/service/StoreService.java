package zerobase.reservation.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.reservation.domain.Store;
import zerobase.reservation.dto.StoreDto;
import zerobase.reservation.dto.StoreSearchDto;
import zerobase.reservation.repository.StoreRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Builder
@Transactional
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    // 매장 등록
    public void storeRegistration(StoreDto storeDto) {
        Store store = storeRepository.save(Store.builder()
                .accountUsername(storeDto.getAccountUsername())
                .storeName(storeDto.getStoreName())
                .storeAddress(storeDto.getStoreAddress())
                .storeInformation(storeDto.getStoreInformation())
                .build());
    }

    // 매장 수정
    public void updateStore(Store store, StoreDto storeDto) {
            if (storeDto.getStoreName() != null) {
                store.setStoreName(storeDto.getStoreName());
            }

            if (storeDto.getStoreAddress() != null) {
                store.setStoreAddress(storeDto.getStoreAddress());
            }

            if (storeDto.getStoreInformation() != null) {
                store.setStoreInformation(storeDto.getStoreInformation());
            }

            store.setReservationAvailable(storeDto.isReservationAvailable());

            storeRepository.save(store);
    }

    // 모든 매장 조회
    public Page<String> getAllStoreNames(Pageable pageable) {
        Page<Store> allStores = this.storeRepository.findAll(pageable);

        List<String> storeNames = allStores.getContent().stream()
                .map(Store::getStoreName)
                .collect(Collectors.toList());

        return new PageImpl<>(storeNames, pageable, allStores.getTotalElements());
    }

    // 특정 매장 상세 조회
    public Optional<StoreSearchDto> getStoreByStoreName(String storeName) {
        Optional<Store> storeOptional = this.storeRepository.findByStoreName(storeName);
        return storeOptional.map(StoreSearchDto::forSearchStore);
    }
}
