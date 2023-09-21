package sang.se.bookingmovie.app.movie_status;

import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieStatusRepository extends JpaRepository<MovieStatusEntity, Integer> {
}
