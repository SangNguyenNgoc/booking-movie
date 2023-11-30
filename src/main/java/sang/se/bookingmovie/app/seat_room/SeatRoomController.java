package sang.se.bookingmovie.app.seat_room;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sang.se.bookingmovie.error.ErrorResponse;
import sang.se.bookingmovie.response.ListResponse;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class SeatRoomController {
    private final SeatRoomService seatRoomService;

    @Operation(
            summary = "Thêm ghế vào phòng",
            description = "Thêm ghế vào phòng",
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
                    )
            }
    )
    @PostMapping("admin/room/{roomId}/seatRoom")
    public ResponseEntity<?> addSeat(
            @RequestBody List<SeatRoomRequest> seatRoomRequest,
            @PathVariable(name = "roomId") String roomId
    ) {
        return ResponseEntity.status(201)
                .body(seatRoomService.create(seatRoomRequest, roomId));
    }

    @Operation(
            summary = "Lấy ghế trong 1 phòng chiếu",
            description = "Lấy tất cả ghế trong 1 phòng từ sơ sở dữ liệu",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ListResponse.class)
                            )
                    )
            }
    )
    @GetMapping("/admin/room/{roomId}/seat-room")
    public ResponseEntity<?> getSeatRoom(@PathVariable(name = "roomId") String roomId) {
        return ResponseEntity.status(200)
                .body(seatRoomService.getSeatRoom(roomId));
    }
}
