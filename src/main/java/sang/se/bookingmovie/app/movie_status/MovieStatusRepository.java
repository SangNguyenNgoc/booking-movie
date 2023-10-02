package sang.se.bookingmovie.app.movie_status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MovieStatusRepository extends JpaRepository<MovieStatusEntity, Integer> {

    @Query("SELECT ms FROM MovieStatusEntity ms " +
            "LEFT JOIN FETCH ms.movies " +
            "WHERE ms.slug = :slug")
    Optional<MovieStatusEntity> findBySlug(@Param("slug") String slug);
}
