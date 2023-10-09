package sang.se.bookingmovie.app.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sang.se.bookingmovie.app.format.Format;
import sang.se.bookingmovie.app.movie_genre.MovieGenre;

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
    private MovieGenre genre;

    @NotNull(message = "Rated MUST not be null")
    @Min(value = 8, message = "Age for rated MUST be at least 8")
    @Max(value = 18, message = "Age for rated MUST be less than or equal to 18")
    private Integer rated;

    @NotNull(message = "Formats MUST not be null")
    private List<Format> formats;

    @AssertTrue(message = "The release date MUST be at least 15 days before the end date of screening")
    public Boolean checkReleaseAndEndDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.DATE, -15);
        Date adjustedEndDate = new Date(calendar.getTimeInMillis());
        return releaseDate.before(adjustedEndDate);
    }
}
