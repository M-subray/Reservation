package zerobase.reservation.dto;

import lombok.*;
import zerobase.reservation.domain.Store;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreSearchDto {

    private String storeName;
    private String storeAddress;
    private String storeInformation;
    private boolean reservationAvailable;

    public static StoreSearchDto forSearchStore(Store store) {
        return StoreSearchDto.builder()
                .storeName(store.getStoreName())
                .storeAddress(store.getStoreAddress())
                .storeInformation(store.getStoreInformation())
                .reservationAvailable(store.isReservationAvailable())
                .build();
    }

    public String getReservationStatus() {
        return reservationAvailable ? "예약 가능" : "예약 불가능";
    }
}
