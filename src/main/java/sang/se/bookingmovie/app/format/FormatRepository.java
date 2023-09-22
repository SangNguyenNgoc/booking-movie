package sang.se.bookingmovie.app.format;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormatRepository extends JpaRepository<FormatEntity, Integer> {
}
