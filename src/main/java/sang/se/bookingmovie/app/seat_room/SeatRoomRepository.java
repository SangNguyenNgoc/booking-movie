package sang.se.bookingmovie.app.seat_room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sang.se.bookingmovie.app.room.RoomEntity;

import java.util.List;

@Repository
public interface SeatRoomRepository extends JpaRepository<SeatRoomEntity, Integer> {
    @Query("SELECT sr FROM SeatRoomEntity sr " +
            "WHERE sr.room.id = :roomId")
    List<SeatRoomEntity> findAllByRoomId(@Param("roomId") String roomId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO `booking_movie`.`seats_rooms` (`row`, `row_index`, `room_id`, `seat_type_id`) " +
            "VALUES (:row, :rowIndex, :status, :roomId, :seatTypeId)", nativeQuery = true)
    void insertSeatRoom(@Param("row") String row,
                        @Param("rowIndex") Integer rowIndex,
                        @Param("status") Integer status,
                        @Param("roomId") String roomId,
                        @Param("seatTypeId") Integer seatTypeId
    );
}
