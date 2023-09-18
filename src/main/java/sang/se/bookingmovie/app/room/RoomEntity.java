package sang.se.bookingmovie.app.room;

import jakarta.persistence.*;
import lombok.*;
import sang.se.bookingmovie.app.cinema.CinemaEntity;
import sang.se.bookingmovie.app.seat_room.SeatRoomEntity;
import sang.se.bookingmovie.app.showtime.ShowtimeEntity;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "rooms")
public class RoomEntity {

    @Id
    @Column(name = "room_id")
    private String id;

    @Column(name = "total_seats")
    private Integer totalSeats;

    @Column(name = "available_seats")
    private Integer availableSeats;

    @OneToMany(
            mappedBy = "room",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Set<ShowtimeEntity> showtimes;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(
            name = "cinema_id",
            referencedColumnName = "cinema_id",
            nullable = false
    )
    private CinemaEntity cinema;

    @OneToMany(
            mappedBy = "room",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Set<SeatRoomEntity> seats;

}
