package sang.se.bookingmovie.app.movie_status;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sang.se.bookingmovie.app.movie.MovieResponse;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieStatus {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @NotBlank(message = "Description must not be blank")
    private String description;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String slug;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    List<MovieResponse> movies;
}
