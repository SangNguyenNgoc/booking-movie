package sang.se.bookingmovie.app.ticket;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sang.se.bookingmovie.app.cinema.CinemaResponse;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TicketResponse {
    private String id;
    private String showtime;
    private String movie;
    private String cinema;
    @JsonProperty("still_valid")
    private Boolean stillValid;
    @JsonInclude(JsonInclude.Include.NON_NULL) private String room;
    @JsonInclude(JsonInclude.Include.NON_NULL) private String seat;
    @JsonInclude(JsonInclude.Include.NON_NULL) private Double price;
    @JsonProperty("cinema_address")
    @JsonInclude(JsonInclude.Include.NON_NULL) private String cinemaAddress;
}
