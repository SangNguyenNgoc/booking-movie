package sang.se.bookingmovie.app.bill;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sang.se.bookingmovie.app.bill_status.BillStatus;
import sang.se.bookingmovie.app.ticket.TicketResponse;
import sang.se.bookingmovie.app.user.UserResponse;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillResponse {
    private String id;
    @JsonProperty("create_time")
    private String createTime;
    @JsonProperty("payment_at")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String paymentAt;
    @JsonProperty("changed_point")
    private Integer changedPoint;
    private Double total;
    private BillStatus status;
    @JsonProperty("cancel_date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String cancelDate;
    @JsonProperty("cancel_reason")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String cancelReason;
    private UserResponse user;
    private List<TicketResponse> tickets;
}
