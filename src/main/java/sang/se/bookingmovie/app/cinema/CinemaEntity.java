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

    @OneToMany(
            mappedBy = "cinema",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Set<RoomEntity> rooms;

    public void update(Cinema cinema){
        if (cinema.getName() != null)name = cinema.getName();
        if (cinema.getAddress() != null)address = cinema.getAddress();
        if (cinema.getCity() != null)city = cinema.getCity();
        if (cinema.getDistrict() != null)district = cinema.getDistrict();
    }

}
