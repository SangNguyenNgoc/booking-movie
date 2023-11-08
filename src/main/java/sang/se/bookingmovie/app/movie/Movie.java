package sang.se.bookingmovie.app.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sang.se.bookingmovie.app.format.Format;
import sang.se.bookingmovie.app.movie_genre.MovieGenre;
import sang.se.bookingmovie.app.movie_status.MovieStatus;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    @NotBlank(message = "Name MUST not be plank")
    private String name;

    @NotBlank(message = "Sub name MUST not be plank")
    private String subName;

    @NotBlank(message = "Director MUST not be plank")
    private String director;

    @NotBlank(message = "Cast MUST not be plank")
    private String cast;

    @NotNull(message = "Release date MUST not be null")
    @Future(message = "Release date MUST be future")
    private Date releaseDate;

    @NotNull(message = "End date MUST not be null")
    @Future(message = "End date MUST be future")
    private Date endDate;

    @NotNull(message = "Running time MUST not be null")
    @Digits(integer = 3, fraction = 2, message = "Running time MUST not be number")
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
    private List<Integer> genre;

    @NotNull(message = "Rated MUST not be null")
    @Min(value = 8, message = "Age for rated MUST be at least 8")
    @Max(value = 18, message = "Age for rated MUST be less than or equal to 18")
    private Integer rated;

    @NotNull(message = "Formats MUST not be null")
    private List<Integer> formats;

    @Min(value = 1, message = "Age for rated MUST be at least 1")
    @Max(value = 4, message = "Age for rated MUST be less than or equal to 4")
    private Integer statusId;

}
