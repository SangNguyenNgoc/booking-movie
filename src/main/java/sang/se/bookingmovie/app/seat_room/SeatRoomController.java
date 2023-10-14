package sang.se.bookingmovie.app.seat_room;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sang.se.bookingmovie.error.ErrorResponse;
import sang.se.bookingmovie.response.ListResponse;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class SeatRoomController {
    private final SeatRoomService seatRoomService;

    @Operation(
            description = "Thêm ghế vào phòng",
            summary = "Api Thêm ghế vào phòng",
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
    @PostMapping("admin/room/{roomId}/seat-room")
    public ResponseEntity<?> addSeat(@RequestBody List<SeatRoomRequest> seatRoomRequest, @PathVariable(name = "roomId") String roomId){
        return ResponseEntity.status(201)
                .body(seatRoomService.create(seatRoomRequest, roomId));
    }

    @Operation(
            description = "Lấy tất cả ghế trong phòng từ sơ sở dữ liệu",
            summary = "Api lấy ghế trong phòng chiếu",
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
    @GetMapping("room/{roomId}/seat-room")
    public ResponseEntity<?> getSeatRoom( @PathVariable(name = "roomId") String roomId){
        return ResponseEntity.status(200)
                .body(seatRoomService.getSeatRoom(roomId));
    }
}
