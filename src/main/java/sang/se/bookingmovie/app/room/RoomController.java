package sang.se.bookingmovie.app.room;

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
@RequestMapping(value = "/api/v1")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @Operation(
            summary = "Thêm phòng phim",
            description = "Tạo phòng phim và thêm vào cơ sở dữ liệu",
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
    @PostMapping(value = "admin/cinema/{cinemaId}/room")
    public ResponseEntity<?> create(
            @RequestBody RoomReq roomRequest,
            @PathVariable(name = "cinemaId") String cinemaId
    ) {
        return ResponseEntity.status(201)
                .body(roomService.create(roomRequest, cinemaId));
    }


    @Operation(
            summary = "Lấy phòng chiếu phim",
            description = "Lấy tất cả phòng chiếu phim từ sơ sở dữ liệu",
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
    @GetMapping(value = "/admin/rooms")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(roomService.getAll());
    }

    @Operation(
            summary = "Lấy phòng chiếu phim",
            description = "Lấy tất cả phòng chiếu phim theo rạp phim từ sơ sở dữ liệu",
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

    @GetMapping(value = "/admin/cinema/{cinemaId}/rooms")
    public ResponseEntity<?> getAllByCinema(@PathVariable(name = "cinemaId") String cinemaId) {
        return ResponseEntity.ok(roomService.getAllByCinema(cinemaId));
    }

    @Operation(
            summary = "Lấy phòng chiếu phim theo id",
            description = "Lấy tất cả phòng chiếu phim theo rạp từ sơ sở dữ liệu",
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
    @GetMapping(value = "/admin/cinema/{cinemaId}/rooms/name")
    public ResponseEntity<?> getAllByName(
            @PathVariable(name = "cinemaId") String cinemaId,
            @RequestParam(name = "name") String name,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size
    ) {
        return ResponseEntity.ok(roomService.getAllByName(cinemaId, name, page, size));
    }

    @Operation(
            summary = "Chỉnh sửa trạng thái của phong",
            description = "API thay đổi trạng thái của phong. " +
                    "API này chỉ dành cho trang admin.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            description = "Data not found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = sang.se.bookingmovie.response.ErrorResponse.class)
                            )
                    )
            }
    )
    @PutMapping(value = "/admin/room/{roomId}/status/{statusId}")
    public ResponseEntity<?> updateStatus(
            @PathVariable(value = "rooomId") String roomId,
            @PathVariable(value = "statusId") Integer statusId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(roomService.updateStatusOfRoom(roomId, statusId));
    }
}
