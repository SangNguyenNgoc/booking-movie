package sang.se.bookingmovie.app.showtime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sang.se.bookingmovie.app.cinema.CinemaEntity;
import sang.se.bookingmovie.app.movie.MovieEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @Query("SELECT c FROM CinemaEntity c " +
            "LEFT JOIN FETCH c.rooms r " +
            "LEFT JOIN FETCH r.showtimes s " +
            "LEFT JOIN FETCH s.movie mv ")
    List<CinemaEntity> findByCinemaWithRoom();

    @Query("SELECT st FROM ShowtimeEntity st " +
            "JOIN st.room r " +
            "WHERE r.cinema.id = :cinemaId AND st.startDate = :date")
    List<ShowtimeEntity> findByCinemaId(@Param("date") Date date, @Param("cinemaId") String movieId);

    @Query("SELECT st FROM ShowtimeEntity st " +
            "WHERE st.startDate = :date AND st.room.id = :roomId AND st.status = true")
    List<ShowtimeEntity> findByDateAndRoom(@Param("date") Date date, @Param("roomId") String roomId);


    @Query("SELECT mv FROM MovieEntity mv " +
            "JOIN FETCH mv.showtimes s " +
            "JOIN FETCH s.room r " +
            "JOIN FETCH r.cinema c " +
            "WHERE s.status = true")
    List<MovieEntity> findByStatus();

    @Query("SELECT st FROM ShowtimeEntity st " +
            "JOIN FETCH st.room r " +
            "JOIN FETCH r.status " +
            "JOIN FETCH r.seats s " +
            "LEFT JOIN FETCH s.tickets " +
            "JOIN FETCH s.type " +
            "JOIN FETCH r.cinema " +
            "JOIN FETCH st.movie mv " +
            "JOIN FETCH mv.status " +
            "JOIN FETCH mv.formats " +
            "WHERE st.id = :showtimeId")
    Optional<ShowtimeEntity> findShowtimeAndSeat(@Param("showtimeId") String showtimeId);
}
