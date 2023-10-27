package sang.se.bookingmovie.app.bill;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<BillEntity, String> {
    Optional<BillEntity> findByTransactionId(String transactionId);

    @Query("SELECT b FROM BillEntity b " +
            "JOIN b.user u " +
            "WHERE u.id = :userId")
    List<BillEntity> findByUser(String userId, Pageable pageable);

    @Query("SELECT b FROM BillEntity b " +
            "JOIN b.user u " +
            "WHERE u.id = :userId AND FUNCTION('DATE', b.createTime) = :date")
    List<BillEntity> findByUser(String userId, Date date, Pageable pageable);
}
