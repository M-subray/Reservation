package zerobase.reservation.domain;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "store_storeName")
    private String storeName;


    private String customerUsername;

    private LocalDateTime visitedDateTime;

    private String review;
}
