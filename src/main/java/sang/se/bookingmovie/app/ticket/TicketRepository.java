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
            "JOIN t.showtime s " +
            "JOIN t.seatRoom sr " +
            "WHERE s.id = :showtimeId AND sr.id = :seatId")
    List<TicketEntity> findByShowtime(
            @Param("showtimeId") String showtimeId,
            @Param("seatId") Integer seatId
    );

    @Query("SELECT t FROM TicketEntity t " +
            "JOIN FETCH t.bill b " +
            "JOIN FETCH b.user u " +
            "JOIN FETCH t.showtime s " +
            "WHERE u.id = :userId AND b.status.id = 2 " +
            "ORDER BY t.showtime.startDate, t.showtime.startTime, t.seatRoom.id ASC")
    List<TicketEntity> findByUser(
            @Param("userId") String userId
    );

    @Query("SELECT t FROM TicketEntity t " +
            "JOIN FETCH t.bill b " +
            "WHERE b.id = :billId AND b.status.id = 2 " +
            "ORDER BY t.showtime.startDate, t.showtime.startTime, t.seatRoom.id ASC")
    List<TicketEntity> findByBill(
            @Param("billId") String billId
    );
}
