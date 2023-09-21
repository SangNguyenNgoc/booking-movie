package sang.se.bookingmovie.app.format;

import jakarta.persistence.*;
import lombok.*;
import sang.se.bookingmovie.app.showtime.ShowtimeEntity;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "formats")
public class FormatEntity {

    @Id
    @Column(name = "format_id")
    private String id;

    private String caption;

    private String version;

    @OneToMany(
            mappedBy = "format",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Set<ShowtimeEntity> showtimes;
}
