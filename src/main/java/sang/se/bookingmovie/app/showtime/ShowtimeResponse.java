package sang.se.bookingmovie.app.showtime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sang.se.bookingmovie.app.format.Format;
import sang.se.bookingmovie.app.room.RoomResponse;

import java.sql.Time;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShowtimeResponse {
    private String id;
    @JsonProperty("start_date") private String startDate;
    @JsonProperty("start_time") private Time startTime;
    @JsonProperty("running_time") private Integer runningTime;
    private Boolean status;
    @JsonInclude(JsonInclude.Include.NON_NULL) private Format format;
    @JsonInclude(JsonInclude.Include.NON_NULL) private RoomResponse room;
}
