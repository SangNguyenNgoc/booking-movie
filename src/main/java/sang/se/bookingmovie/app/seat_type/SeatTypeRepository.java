package sang.se.bookingmovie.app.seat_type;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatTypeRepository extends JpaRepository<SeatTypeEntity, Integer> {

}
