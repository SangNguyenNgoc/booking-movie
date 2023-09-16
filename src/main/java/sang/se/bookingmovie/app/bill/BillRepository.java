package sang.se.bookingmovie.app.bill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<BillEntity, String> {
}
