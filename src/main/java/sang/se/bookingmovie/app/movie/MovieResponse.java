package sang.se.bookingmovie.app.movie;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sang.se.bookingmovie.app.cinema.CinemaResponse;
import sang.se.bookingmovie.app.comment.Comment;
import sang.se.bookingmovie.app.format.Format;
import sang.se.bookingmovie.app.movie_genre.MovieGenre;
import sang.se.bookingmovie.app.movie_img.MovieImage;
import sang.se.bookingmovie.app.movie_status.MovieStatus;
import sang.se.bookingmovie.app.showtime.ShowtimeResponse;

import java.util.List;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MovieResponse{
        private String id;
        private String name;
        @JsonProperty("sub_name") private String subName;
        private String director;
        private String cast;
        @JsonProperty("release_date") private String releaseDate;
        @JsonProperty("end_date") private String endDate;
        @JsonProperty("running_time") private Integer runningTime;
        private Double rating;
        @JsonProperty("number_of_ratings") private Integer numberOfRatings;
        private String language;
        private String description;
        private String trailer;
        private String poster;
        @JsonProperty("horizontal_poster") private String horizontalPoster;
        private String producer;
        private String rated;
        private String slug;
        @JsonInclude(JsonInclude.Include.NON_NULL) private MovieGenre genre;
        @JsonInclude(JsonInclude.Include.NON_NULL) private MovieStatus status;
        @JsonInclude(JsonInclude.Include.NON_NULL) private List<Format> formats;
        @JsonInclude(JsonInclude.Include.NON_EMPTY) private List<MovieImage> images;
        @JsonInclude(JsonInclude.Include.NON_EMPTY) private List<ShowtimeResponse> showtimes;
        @JsonInclude(JsonInclude.Include.NON_EMPTY) private List<Comment> comments;
        @JsonInclude(JsonInclude.Include.NON_EMPTY) private List<CinemaResponse> cinemas;
}
