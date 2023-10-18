package sang.se.bookingmovie.app.cinema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sang.se.bookingmovie.app.room.RoomResponse;
import sang.se.bookingmovie.app.showtime.ShowtimeResponse;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CinemaResponse {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    @NotBlank(message = "Cinema name must not be blank")
    @NotNull(message = "Cinema name must not be null")
    private String name;

    @NotBlank(message = "Cinema address must not be blank")
    @NotNull(message = "Cinema address must not be null")
    private String address;

    @NotBlank(message = "Cinema district must not be blank")
    @NotNull(message = "Cinema district must not be null")
    private String district;

    @NotBlank(message = "Cinema city must not be blank")
    @NotNull(message = "Cinema city must not be null")
    private String city;

    private List<ShowtimeResponse> showtime;
}
