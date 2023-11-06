package sang.se.bookingmovie.app.bill;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<BillEntity, String> {
    Optional<BillEntity> findByTransactionId(String transactionId);

    @Query("SELECT b FROM BillEntity b " +
            "JOIN b.user u " +
            "WHERE u.id = :userId")
    List<BillEntity> findByUser(String userId, Pageable pageable);

    @Query("SELECT b FROM BillEntity b " +
            "JOIN b.user u " +
            "WHERE u.id = :userId AND FUNCTION('DATE', b.createTime) = :date")
    List<BillEntity> findByUser(String userId, Date date, Pageable pageable);

    @Query("SELECT SUM(b.total) FROM BillEntity b " +
            "WHERE MONTH(b.createTime) = :month " +
            "AND YEAR(b.createTime) = :year " +
            "AND b.status.id = 2"
    )
    Optional<Double> findRevenueByMonth(
            @Param("month") int month,
            @Param("year") int year
    );

    @Query("SELECT SUM(b.total) FROM BillEntity b " +
            "WHERE MONTH(b.createTime) = :month " +
            "AND YEAR(b.createTime) = :year " +
            "AND DAY(b.createTime) = :day " +
            "AND b.status.id = 2"
    )
    Optional<Double> findRevenueByDayAndMonth(
            @Param("day") Integer day,
            @Param("month") Integer month,
            @Param("year") Integer year
    );

    @Query("SELECT COUNT(t) FROM TicketEntity t " +
            "JOIN t.bill b " +
            "WHERE MONTH(b.createTime) = :month " +
            "AND YEAR(b.createTime) = :year " +
            "AND b.status.id = 2"
    )
    Optional<Integer> findTotalTicketByMonth(
            @Param("month") int month,
            @Param("year") int year
    );

    @Query("SELECT COUNT(t) FROM TicketEntity t " +
            "JOIN t.bill b " +
            "WHERE MONTH(b.createTime) = :month " +
            "AND YEAR(b.createTime) = :year " +
            "AND DAY(b.createTime) = :day " +
            "AND b.status.id = 2"
    )
    Optional<Integer> findTotalTicketByDayAndMonth(
            @Param("day") Integer day,
            @Param("month") Integer month,
            @Param("year") Integer year
    );

    @Query("SELECT b FROM BillEntity b " +
            "JOIN FETCH b.tickets t " +
            "JOIN FETCH t.showtime st " +
            "JOIN FETCH st.room r " +
            "WHERE MONTH(b.createTime) = :month " +
            "AND YEAR(b.createTime) = :year " +
            "AND r.cinema.id = :cinemaId " +
            "AND b.status.id = 2"
    )
    List<BillEntity> findRevenueByMonthAndCinema(
            @Param("month") int month,
            @Param("year") int year,
            @Param("cinemaId") String cinemaId
    );

    @Query("SELECT b FROM BillEntity b " +
            "JOIN FETCH b.tickets t " +
            "JOIN FETCH t.showtime st " +
            "WHERE MONTH(b.createTime) = :month " +
            "AND YEAR(b.createTime) = :year " +
            "AND st.movie.id = :movieId " +
            "AND b.status.id = 2"
    )
    List<BillEntity> findRevenueByMonthAndMovie(
            @Param("month") int month,
            @Param("year") int year,
            @Param("movieId") String movieId
    );

}
