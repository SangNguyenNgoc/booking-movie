package sang.se.bookingmovie.app.showtime;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowtimeRepository extends JpaRepository<ShowtimeEntity, String> {
}
