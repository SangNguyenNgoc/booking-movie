package sang.se.bookingmovie.app.cinema;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaRepository extends JpaRepository<CinemaEntity, String> {

}
