package sang.se.bookingmovie.app.movie_status;

import jakarta.persistence.*;
import lombok.*;
import sang.se.bookingmovie.app.movie.MovieEntity;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "movies_status")
public class MovieStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_status_id")
    private Integer id;

    private String description;

    private String slug;

    @OneToMany(
            mappedBy = "status",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Set<MovieEntity> movies;
}
