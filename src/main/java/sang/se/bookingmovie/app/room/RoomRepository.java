package sang.se.bookingmovie.app.room;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, String> {
    @Query("SELECT r FROM RoomEntity r " +
            "WHERE r.cinema.id = :cinemaId")
    List<RoomEntity> findAllByCinemaId(@Param("cinemaId") String cinemaId);

    @Query("SELECT r FROM RoomEntity r " +
            "WHERE r.cinema.id = :cinemaId AND r.name LIKE :name")
    Page<RoomEntity> findByNameInCinema(@Param("cinemaId") String cinemaId, @Param("name") String name, Pageable pageable);


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO `booking_movie`.`rooms` " +
            "(`room_id`, `available_seats`, `name`, `total_seats`, `cinema_id`, `slug`, `status_id`) VALUES " +
            "(:roomId, :availableSeats, :name, :totalSeats, :cinemaId, :slug, 1);", nativeQuery = true)
    void insertRoom(@Param("roomId") String roomId,
                    @Param("availableSeats") Integer availableSeats,
                    @Param("name") String name,
                    @Param("totalSeats") Integer totalSeats,
                    @Param("cinemaId") String cinemaId,
                    @Param("slug") String slug
    );
}
