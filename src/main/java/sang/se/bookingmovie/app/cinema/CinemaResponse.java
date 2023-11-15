package sang.se.bookingmovie.app.cinema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sang.se.bookingmovie.app.movie.MovieResponse;
import sang.se.bookingmovie.app.room.RoomResponse;
import sang.se.bookingmovie.app.showtime.ShowtimeResponse;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CinemaResponse {
    private String id;
    private String name;
    private String address;
    private String district;
    private String city;
    private String description;
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String status;
    @JsonInclude(JsonInclude.Include.NON_EMPTY) private List<ShowtimeResponse> showtime;
    @JsonInclude(JsonInclude.Include.NON_EMPTY) private List<MovieResponse> movies;
    @JsonInclude(JsonInclude.Include.NON_NULL) private List<RoomResponse> rooms;
}
