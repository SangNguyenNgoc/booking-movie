package sang.se.bookingmovie.app.bill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<BillEntity, String> {
    Optional<BillEntity> findByTransactionId(String transactionId);
}
