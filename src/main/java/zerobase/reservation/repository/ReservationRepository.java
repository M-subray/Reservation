package zerobase.reservation.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.reservation.domain.Reservation;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByUsername(String username);
    Optional<List<Reservation>> findByStoreName(String storeName);
    Optional<Reservation> findByStoreNameAndUsername(String storeName, String username);
}
