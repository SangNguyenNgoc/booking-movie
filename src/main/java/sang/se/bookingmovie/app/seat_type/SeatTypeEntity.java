package sang.se.bookingmovie.app.seat_type;

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
@Table(name = "seats_type")
public class SeatTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_type_id")
    private Integer id;

    private String name;

    @OneToMany(
            mappedBy = "type",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Set<SeatRoomEntity> seats;

    private Double price;

}
