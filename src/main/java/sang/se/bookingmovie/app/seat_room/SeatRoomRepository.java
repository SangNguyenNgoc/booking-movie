package sang.se.bookingmovie.app.seat_room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sang.se.bookingmovie.app.room.RoomEntity;

import java.util.List;

public interface SeatRoomRepository extends JpaRepository<SeatRoomEntity, String> {
    @Query("SELECT sr FROM SeatRoomEntity sr " +
            "WHERE sr.room.id = :roomId")
    List<SeatRoomEntity> findAllByRoomId(@Param("roomId") String roomId);
}
