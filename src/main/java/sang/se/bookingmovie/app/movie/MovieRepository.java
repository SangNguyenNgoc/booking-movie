package sang.se.bookingmovie.app.movie;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, String> {

    Optional<MovieEntity> findBySlug(String slug);

    @Query("SELECT mv FROM MovieEntity mv " +
            "WHERE mv.slug LIKE %:input%")
    List<MovieEntity> searchBySlug(@Param(value = "input") String input, Pageable pageable);

    @Query("SELECT mv FROM MovieEntity mv " +
            "JOIN FETCH mv.status s " +
            "WHERE s.slug = :slug")
    List<MovieEntity> findByStatus(@Param("slug") String slug, Pageable pageable);

    @Query("SELECT mv FROM MovieEntity mv " +
            "JOIN FETCH mv.status s " +
            "WHERE s.slug = 'coming-soon' OR s.slug = 'showing-now'")
    List<MovieEntity> findByComingAndShowing();

    @Query("SELECT mv FROM MovieEntity mv " +
            "JOIN mv.status s " +
            "WHERE mv.id = :movieId AND s.id != 4")
    Optional<MovieEntity> findByAddShowtime(@Param("movieId") String movieId);

    @Query("SELECT mv FROM MovieEntity mv " +
            "WHERE MONTH(mv.releaseDate) <= :month " +
            "AND YEAR(mv.releaseDate) = :year " +
            "AND MONTH(mv.endDate) >= :month " +
            "AND YEAR(mv.endDate) = :year")
    List<MovieEntity> findByMonthYear(
            @Param("month") int month,
            @Param("year") int year
    );
}
