package sang.se.bookingmovie.app.room;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<RoomEntity, String> {
}
