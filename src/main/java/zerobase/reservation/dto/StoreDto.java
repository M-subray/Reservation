package zerobase.reservation.dto;

import lombok.*;
import zerobase.reservation.domain.Store;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreDto {

    public interface FirstValidationGroup {}
    public interface SecondValidationGroup {}

    @GroupSequence({FirstValidationGroup.class, SecondValidationGroup.class})
    public interface GroupOrder{}

    private String accountUsername;

    @NotNull(groups = FirstValidationGroup.class)
    @Size(min = 2, max = 30, groups = FirstValidationGroup.class)
    private String storeName;

    @NotNull(groups = SecondValidationGroup.class)
    @Size(min = 2, max = 255,groups = SecondValidationGroup.class)
    private String storeAddress;

    private String storeInformation;

    private boolean reservationAvailable;

    public static StoreDto fromEntity(StoreDto storeDto) {
        return StoreDto.builder()
                .accountUsername(storeDto.getAccountUsername())
                .storeName(storeDto.storeName)
                .storeAddress(storeDto.getStoreAddress())
                .storeInformation(storeDto.getStoreInformation())
                .reservationAvailable(storeDto.isReservationAvailable())
                .build();
    }
}
