package sang.se.bookingmovie.app.seat_room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sang.se.bookingmovie.app.room.RoomEntity;

import java.util.List;

@Repository
public interface SeatRoomRepository extends JpaRepository<SeatRoomEntity, Integer> {
    @Query("SELECT sr FROM SeatRoomEntity sr " +
            "WHERE sr.room.id = :roomId")
    List<SeatRoomEntity> findAllByRoomId(@Param("roomId") String roomId);
}
