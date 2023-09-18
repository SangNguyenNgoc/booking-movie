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

    private String name;

    @OneToMany(
            mappedBy = "seat",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Set<SeatRoomEntity> rooms;
}
