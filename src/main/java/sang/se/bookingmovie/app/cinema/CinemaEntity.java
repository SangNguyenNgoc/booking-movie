package sang.se.bookingmovie.app.cinema;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

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

    private String address;

    private String city;

    private  String district;
}
