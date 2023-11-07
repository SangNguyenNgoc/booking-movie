package sang.se.bookingmovie.app.movie_genre;

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
@Table(name = "movie_genres")
public class MovieGenreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_genre_id")
    private Integer id;

    private String name;

    @ManyToMany(
            mappedBy = "genre",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Set<MovieEntity> movies;

}
