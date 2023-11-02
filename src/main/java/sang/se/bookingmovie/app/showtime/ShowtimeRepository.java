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
            "JOIN FETCH mv.showtimes s " +
            "JOIN FETCH s.room r " +
            "WHERE r.cinema.id = :cinemaId AND s.startDate = :date AND s.status = true")
    List<MovieEntity> findByDateAndCinema(@Param("date") Date date, @Param("cinemaId") String cinemaId);

    @Query("SELECT c FROM CinemaEntity c " +
            "JOIN FETCH c.rooms r " +
            "JOIN FETCH r.showtimes s " +
            "WHERE s.movie.id = :movieId AND s.startDate = :date AND s.status = true")
    List<CinemaEntity> findByMovieAndDate(@Param("date") Date date, @Param("movieId") String movieId);

    @Query("SELECT st FROM ShowtimeEntity st " +
            "JOIN st.room r " +
            "WHERE r.cinema.id = :cinemaId AND st.startDate = :date")
    List<ShowtimeEntity> findByCinemaId(@Param("date") Date date, @Param("cinemaId") String movieId);

    @Query("SELECT st FROM ShowtimeEntity st " +
            "WHERE st.startDate = :date AND st.room.id = :roomId"
    )
    List<ShowtimeEntity> findByDateAndRoom(@Param("date") Date date, @Param("roomId") String roomId);

    @Query("SELECT mv FROM MovieEntity mv " +
            "JOIN FETCH mv.showtimes s " +
            "WHERE s.room.cinema.id = :cinemaId AND s.status = true"
    )
    List<MovieEntity> findByCinema(@Param("cinemaId") String cinemaId);
}
