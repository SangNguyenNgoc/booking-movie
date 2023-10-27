package sang.se.bookingmovie.app.ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sang.se.bookingmovie.app.movie.MovieEntity;

import java.util.Date;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, String> {

    @Query("SELECT t FROM TicketEntity t " +
            "JOIN FETCH t.showtime s " +
            "JOIN FETCH t.seatRoom sr " +
            "WHERE s.id = :showtimeId AND sr.id = :seatId")
    List<TicketEntity> findByShowtime(
            @Param("showtimeId") String showtimeId,
            @Param("seatId") Integer seatId
    );
}
