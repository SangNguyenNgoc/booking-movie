package sang.se.bookingmovie.app.seat_room;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DialectOverride;
import sang.se.bookingmovie.app.room.RoomEntity;
import sang.se.bookingmovie.app.seat.SeatEntity;
import sang.se.bookingmovie.app.seat_type.SeatTypeEntity;
import sang.se.bookingmovie.app.ticket.TicketEntity;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "seats_rooms")
public class SeatRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_room_id")
    private Integer id;

    @Column(name = "status", columnDefinition = "boolean default true")
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(
            name = "room_id",
            referencedColumnName = "room_id",
            nullable = false
    )
    private RoomEntity room;

//    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(
//            name = "seat_id",
//            referencedColumnName = "seat_id",
//            nullable = false
//    )
//    private SeatEntity seat;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(
            name = "seat_type_id",
            referencedColumnName = "seat_type_id",
            nullable = false
    )
    private SeatTypeEntity type;

    @OneToMany(
            mappedBy = "seatRoom",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Set<TicketEntity> tickets;

    private String row;

    @Column(name = "row_index")
    private Integer rowIndex;

    @Transient
    private Boolean isReserved;
}
