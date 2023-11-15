package sang.se.bookingmovie.app.cinema;

import jakarta.persistence.*;
import lombok.*;
import sang.se.bookingmovie.app.room.RoomEntity;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "cinemas")
public class CinemaEntity {
    @Id
    @Column(name = "cinema_id")
    private String id;

    @Column(name = "cinema_name")
    private String name;

    private String address;

    private String city;

    private  String district;

    private String description;

    private String slug;

    @Enumerated(EnumType.STRING)
    private CinemaStatus status;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(
            mappedBy = "cinema",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Set<RoomEntity> rooms;


}
