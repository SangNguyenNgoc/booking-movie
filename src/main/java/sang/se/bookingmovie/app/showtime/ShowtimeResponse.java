package sang.se.bookingmovie.app.showtime;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShowtimeResponse {
    private String id;

    @JsonProperty("start_date")
    private Date startDate;

    @JsonProperty("start_time")
    private Time startTime;

    private Boolean status;
}
