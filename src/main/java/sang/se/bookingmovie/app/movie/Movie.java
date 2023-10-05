package sang.se.bookingmovie.app.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sang.se.bookingmovie.app.format.FormatEntity;
import sang.se.bookingmovie.app.movie_genre.MovieGenre;
import sang.se.bookingmovie.app.movie_status.MovieStatus;

import java.sql.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    @NotBlank(message = "Name MUST not be plank")
    private String name;

    @NotBlank(message = "Director MUST not be plank")
    private String director;

    @NotBlank(message = "Cast MUST not be plank")
    private String cast;

    @NotNull(message = "Release date MUST not be null")
    @Future(message = "Release date MUST be future")
    private Date releaseDate;

    @NotNull(message = "Movie's name MUST not be null")
    private Integer runningTime;

    @NotBlank(message = "Language MUST not be plank")
    private String language;

    @NotBlank(message = "Description MUST not be plank")
    private String description;

    @NotBlank(message = "Trailer MUST not be plank")
    private String trailer;

    @NotBlank(message = "Producer MUST not be plank")
    private String producer;

    @NotNull(message = "Genre MUST not be null")
    private MovieGenre genre;

    @NotNull(message = "Status MUST not be null")
    private MovieStatus status;

    @NotNull(message = "Format MUST not be null")
    private Set<FormatEntity> formats;


}
