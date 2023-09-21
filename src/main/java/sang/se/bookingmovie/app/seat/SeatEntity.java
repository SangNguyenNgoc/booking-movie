package sang.se.bookingmovie.app.seat;

import jakarta.persistence.*;
import lombok.*;
import sang.se.bookingmovie.app.seat_room.SeatRoomEntity;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "seats")
public class SeatEntity {

    @Id
    @Column(name = "seat_id")
    private String id;

    @Column(name = "position_x")
    private Integer positionX;

    @Column(name = "position_y")
    private Integer positionY;

    @OneToMany(
            mappedBy = "seat",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Set<SeatRoomEntity> rooms;
}
