package sang.se.bookingmovie.app.bill;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sang.se.bookingmovie.app.bill_status.BillStatus;
import sang.se.bookingmovie.app.user.User;
import sang.se.bookingmovie.app.user.UserResponse;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillResponse {
    private String id;
    @JsonProperty("create_time") private String createTime;
    @JsonProperty("payment_at") @JsonInclude(JsonInclude.Include.NON_NULL) private String paymentAt;
    @JsonProperty("changed_point") private Integer changedPoint;
    private Double total;
    private BillStatus status;
    @JsonProperty("cancel_date" )@JsonInclude(JsonInclude.Include.NON_NULL) private String cancelDate;
    @JsonProperty("cancel_reason") @JsonInclude(JsonInclude.Include.NON_NULL) private String cancelReason;
    private UserResponse user;
}
