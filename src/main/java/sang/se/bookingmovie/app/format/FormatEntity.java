package sang.se.bookingmovie.app.format;

import jakarta.persistence.*;
import lombok.*;
import sang.se.bookingmovie.app.movie.MovieEntity;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "format_id")
    private Integer id;

    private String caption;

    private String version;

    @OneToMany(
            mappedBy = "format",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Set<ShowtimeEntity> showtimes;

    @ManyToMany(
            mappedBy = "formats",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    private Set<MovieEntity> movies;
}
