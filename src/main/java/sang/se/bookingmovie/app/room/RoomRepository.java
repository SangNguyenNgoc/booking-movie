package sang.se.bookingmovie.app.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<RoomEntity, String> {
    @Query("SELECT r FROM RoomEntity r " +
            "WHERE r.cinema.id = :cinemaId")
    List<RoomEntity> findAllByCinemaId(@Param("cinemaId") String cinemaId);

    @Query("SELECT r FROM RoomEntity r " +
            "WHERE r.cinema.id = :cinemaId AND r.name LIKE :name")
    List<RoomEntity> findByNameInCinema(@Param("cinemaId") String cinemaId, @Param("name") String name);
}
