package sang.se.bookingmovie.app.showtime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sang.se.bookingmovie.app.cinema.CinemaEntity;
import sang.se.bookingmovie.app.movie.MovieEntity;

import java.util.Date;
import java.util.List;

public interface ShowtimeRepository extends JpaRepository<ShowtimeEntity, String> {
    @Query("SELECT mv FROM MovieEntity mv " +
            "JOIN mv.showtimes s " +
            "JOIN s.room r " +
            "WHERE r.cinema.id = :cinemaId AND s.startDate = :date")
    List<MovieEntity> findByDateAndCinema(@Param("date") Date date, @Param("cinemaId") String cinemaId);

    @Query("SELECT c FROM CinemaEntity c " +
            "JOIN c.rooms r " +
            "JOIN r.showtimes s " +
            "WHERE s.movie.id = :movieId AND s.startDate = :date")
    List<CinemaEntity> findByMovieAndDate(@Param("date") Date date, @Param("movieId") String movieId);

    @Query("SELECT st FROM ShowtimeEntity st " +
            "JOIN st.room r " +
            "WHERE r.cinema.id = :cinemaId AND st.startDate = :date")
    List<ShowtimeEntity> findByCinemaId(@Param("date") Date date, @Param("cinemaId") String movieId);

}
