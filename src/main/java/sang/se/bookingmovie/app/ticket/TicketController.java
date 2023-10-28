package sang.se.bookingmovie.app.ticket;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sang.se.bookingmovie.error.ErrorResponse;
import sang.se.bookingmovie.response.ListResponse;

@RestController
@RequestMapping("/api/v1")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;


    @Operation(
            summary = "Lấy vé còn hạn sử dụng.",
            description = "Lấy vé còn hạn sử dụng của 1 người dùng.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ListResponse.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Data not found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    @GetMapping(value = "/customer/user/tickets/stillValid")
    public ResponseEntity<?> getTicketInUserAndStillValid(
            @RequestHeader(value = "Authorization") String token
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ticketService.getTicketInUser(token, true));
    }


    @Operation(
            summary = "Lấy vé hết hạn sử dụng.",
            description = "Lấy vé hết hạn sử dụng của 1 người dùng.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ListResponse.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Data not found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    @GetMapping(value = "/customer/user/tickets/expired")
    public ResponseEntity<?> getTicketInUserAndExpired(
            @RequestHeader(value = "Authorization") String token
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ticketService.getTicketInUser(token, false));
    }


    @Operation(
            summary = "Lấy vé của 1 hóa đơn bất kì.",
            description = "Lấy vé của 1 hóa đơn bất kì, lưu ý hóa đơn này phải là của người đang đăng nhập.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ListResponse.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Data not found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    @GetMapping(value = "/customer/bill/{billId}/tickets/")
    public ResponseEntity<?> getTicketInBill(
            @RequestHeader(value = "Authorization") String token,
            @PathVariable(value = "billId") String billId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ticketService.getTicketInBill(token, billId));
    }


    @Operation(
            summary = "Lấy chi tiết 1 vé bất kì.",
            description = "Lấy chi tiết 1 vé bất kì theo id, lưu ý vé này phải là của người đang đăng nhập.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ListResponse.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Data not found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    @GetMapping(value = "/customer/ticket/{ticketId}")
    public ResponseEntity<?> getDetail(
            @RequestHeader(value = "Authorization") String token,
            @PathVariable(value = "ticketId") String ticketId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ticketService.getDetail(token, ticketId));
    }
}
