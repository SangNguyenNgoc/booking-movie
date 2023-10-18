package sang.se.bookingmovie.app.showtime;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Time;
import java.util.Date;;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShowtimeRequest {
    @JsonProperty("start_date")
    @Future(message = "The start date must be in the future")
    @NotNull(message = "Start date must not be null")
    private Date startDate;

    @JsonProperty("start_time")
    @Future(message = "The start time must be in the future")
    @NotNull(message = "Start time must not be null")
    private Time startTime;

    @JsonProperty("running_time")
    @NotNull(message = "running time must not be null")
    private Integer runningTime;

    @JsonProperty("movie_id")
    @NotBlank(message = "movie id must not be blank")
    @NotNull(message = "movie id must not be null")
    private String movie;

    @JsonProperty("format_id")
    @NotNull(message = "format id must not be null")
    private Integer format;

    @JsonProperty("room_id")
    @NotBlank(message = "room id must not be blank")
    @NotNull(message = "room id must not be null")
    private String room;
}
