package zerobase.reservation.utils;

import lombok.RequiredArgsConstructor;
import zerobase.reservation.domain.Store;
import zerobase.reservation.exception.StoreException;
import zerobase.reservation.repository.StoreRepository;
import zerobase.reservation.type.ErrorCode;

import java.util.Optional;

@RequiredArgsConstructor
public class StoreDataAccessUtils {
    private final StoreRepository storeRepository;

    public Store byStoreNameForStore (String storeName) {
        Optional<Store> byStoreName =
                storeRepository.findByStoreName(storeName);

        byStoreName.orElseThrow(() ->
                new StoreException(ErrorCode.STORE_NOT_FOUND));

        return byStoreName.get();
    }

    public Store byAccountUsernameForStore (String accountUsername) {
        Optional<Store> byAccountUsername =
                storeRepository.findByAccountUsername(accountUsername);

        byAccountUsername.orElseThrow(() ->
                new StoreException(ErrorCode.STORE_NOT_FOUND));

        return byAccountUsername.get();
    }
}
