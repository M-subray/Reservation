package zerobase.reservation.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_username")
    private String accountUsername;

    private String storeName;
    private String storeAddress;
    private String storeInformation;

    private boolean reservationAvailable;
}
