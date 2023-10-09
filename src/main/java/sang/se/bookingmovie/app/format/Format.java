package sang.se.bookingmovie.app.format;

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
public class Format {

    private Integer id;

    @NotBlank(message = "Caption MUST not be blank")
    private String caption;

    @NotBlank(message = "Version MUST not be blank")
    private String version;


}
