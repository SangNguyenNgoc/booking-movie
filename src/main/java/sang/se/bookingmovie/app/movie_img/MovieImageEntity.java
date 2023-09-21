package sang.se.bookingmovie.app.movie_img;

import jakarta.persistence.*;
import lombok.*;
import sang.se.bookingmovie.app.movie.MovieEntity;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "movies_images")
public class MovieImageEntity {

    @Id
    @Column(name = "image_id")
    private String id;

    private String path;

    private String extension;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(
            name = "movie_id",
            referencedColumnName = "movie_id",
            nullable = false
    )
    private MovieEntity movie;
}
