package sang.se.bookingmovie.app.bill;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sang.se.bookingmovie.error.ErrorResponse;
import sang.se.bookingmovie.response.ListResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;

@RestController
@RequestMapping("/api/v1")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;

    @Operation(
            summary = "Thêm hóa đơn kèm vé trước khi thanh toán",
            description = "API thêm hóa đơn kèm vé trước khi thanh toán, hóa đơn khi vừa thêm sẽ có trạng thái mặc định " +
                    "là `Unpaid`, thời hạn thanh toán là 15 phút, nếu sau 15 phút không thực hiện thanh toán, hóa đơn và " +
                    "vé sẽ được xóa khỏi cơ sở dữ liệu.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            description = "Data invalid",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
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
    @PostMapping(value = "/customer/bill")
    public ResponseEntity<?> create(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody Bill bill
    ) throws UnsupportedEncodingException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(billService.create(token, bill));
    }


    @Operation(
            summary = "Thực hiện thanh toán",
            description = "API được gọi khi vừa thanh toán và chuyển về trang thông báo, API sẽ trả về kết quả cần thiết " +
                    "để hiển thị với khách hàng, kết quả có thể thành công hoặc thất bại. Khi thất bại, lí do " +
                    "sẽ được nêu rõ. ",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            description = "Data invalid",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Data not found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Payment error",
                            responseCode = "402",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    @PutMapping(value = "/customer/payment/{transactionId}")
    public ResponseEntity<?> pay(
            @RequestHeader(value = "Authorization") String token,
            @PathVariable(value = "transactionId") String transactionId
    ) throws ServletException, JSONException, IOException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(billService.pay(token, transactionId));
    }


    @Operation(
            summary = "Hủy đặt vé",
            description = "API hủy đặt vé, lưu ý chỉ khi chưa thanh toán mới có thể hủy. Khi hủy, hóa đơn sẽ được đổi " +
                    "trạng thái và các vé trong hóa đơn sẽ được xóa.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            description = "Data invalid",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
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
    @PutMapping(value = "/customer/refund/{billId}")
    public ResponseEntity<?> refund(
            @PathVariable(value = "billId") String billId,
            @RequestBody String reason
    ) throws ServletException, JSONException, IOException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(billService.refund(billId, reason));
    }


    @Operation(
            summary = "Lấy danh sách hóa đơn của người dùng",
            description = "Lấy danh sách hóa đơn của người dùng đang đăng nhập, api dùng cho phía người dùng.",
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
                            description = "Data invalid",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
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
    @GetMapping(value = "/customer/user/bills")
    public ResponseEntity<?> getBillByUser(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "date", required = false) Date date
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(billService.getBillByUser(token, page, size, date));
    }


    @Operation(
            summary = "Lấy danh sách hóa đơn của người dùng",
            description = "Lấy danh sách hóa đơn của người dùng bằng email, api dùng cho phía admin.",
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
                            description = "Data invalid",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
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
    @GetMapping(value = "/admin/user/{userId}/bills")
    public ResponseEntity<?> getBillByAdmin(
            @PathVariable(value = "userId") String userId,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "date", required = false) Date date
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(billService.getBillByAdmin(userId, page, size, date));
    }

    @Operation(
            summary = "Lấy chi tiết hóa đơn của người dùng",
            description = "Lấy chi tiết hóa đơn của người dùng đang đăng nhập, api dùng cho phía người dùng.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BillResponse.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Data invalid",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
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
    @GetMapping(value = "/customer/bill/{billId}")
    public ResponseEntity<?> getBillByUser(
            @RequestHeader(value = "Authorization") String token,
            @PathVariable(value = "billId") String billId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(billService.getDetail(token, billId));
    }


    @Operation(
            summary = "Lấy chi tiết hóa đơn của người dùng",
            description = "Lấy chi tiết hóa đơn của người dùng bằng mã hóa đơn, api dùng cho phía admin.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BillResponse.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Data invalid",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
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
    @GetMapping(value = "/admin/bill/{billId}")
    public ResponseEntity<?> getBillByAdmin(
            @PathVariable(value = "billId") String billId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(billService.getDetail(billId));
    }
}
