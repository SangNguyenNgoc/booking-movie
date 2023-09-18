package sang.se.bookingmovie.app.seat_room;

import jakarta.persistence.*;
import lombok.*;
import sang.se.bookingmovie.app.room.RoomEntity;
import sang.se.bookingmovie.app.seat.SeatEntity;
import sang.se.bookingmovie.app.seat_type.SeatTypeEntity;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "seats_rooms")
public class SeatRoomEntity {

    @Id
    @Column(name = "seat_room_id")
    private String id;

    private Boolean status;

    private Double price;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(
            name = "room_id",
            referencedColumnName = "room_id",
            nullable = false
    )
    private RoomEntity room;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(
            name = "seat_id",
            referencedColumnName = "seat_id",
            nullable = false
    )
    private SeatEntity seat;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(
            name = "seat_type_id",
            referencedColumnName = "seat_type_id",
            nullable = false
    )
    private SeatTypeEntity type;
}
