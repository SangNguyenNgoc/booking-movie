package sang.se.bookingmovie.app.room;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sang.se.bookingmovie.error.ErrorResponse;
import sang.se.bookingmovie.response.ListResponse;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @Operation(
            description = "Tạo phòng phim và thêm vào cơ sở dữ liệu",
            summary = "Api thêm phòng phim",
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
    public ResponseEntity<?> create(@RequestBody RoomReq roomRequest, @PathVariable(name = "cinemaId") String cinemaId) {
        return ResponseEntity.status(201)
                .body(roomService.create(roomRequest, cinemaId));
    }


    @Operation(
            description = "Lấy tất cả phòng chiếu phim từ sơ sở dữ liệu",
            summary = "Api lấy phòng chiếu phim",
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
            description = "Lấy tất cả phòng chiếu phim từ sơ sở dữ liệu",
            summary = "Api lấy phòng chiếu phim",
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
    public ResponseEntity<?> getAllByCinema(@PathVariable(name = "cinemaId") String cinemaId){
        return ResponseEntity.ok(roomService.getAllByCinema(cinemaId));
    }

    @Operation(
            description = "Lấy tất cả phòng chiếu phim theo id từ sơ sở dữ liệu",
            summary = "Api lấy phòng chiếu phim theo id",
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
    @GetMapping(value = "/admin/cinema/{cinemaId}/rooms/")
    public ResponseEntity<?> getAllByName(@PathVariable(name = "cinemaId") String cinemaId, @RequestParam("name") String name){
        return ResponseEntity.ok(roomService.getAllByName(cinemaId, name));
    }
}
