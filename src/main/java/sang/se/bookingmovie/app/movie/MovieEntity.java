package sang.se.bookingmovie.app.movie;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "movies")
public class MovieEntity {
    private String id;
    private String name;
    private String director;
    private String cast;
    private String genre;
    private Date releaseDate;
    private Integer runningTime;
}
