package sang.se.bookingmovie.app.cinema;

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

@RestController
@RequestMapping("/api/v1")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class CinemaController {
    private final CinemaService cinemaService;

    @Operation(
            summary = "Api thêm rạp phim",
            description = "Tạo rạp phim và thêm vào cơ sở dữ liệu",
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
    @PostMapping("admin/cinema")
    public ResponseEntity<?> create(@RequestBody Cinema cinema) {
        return ResponseEntity.status(201)
                .body(cinemaService.create(cinema));
    }

    @Operation(
            summary = "Api thêm rạp phim và phòng chiếu",
            description = "Tạo rạp phim và phòng chiếu và thêm vào cơ sở dữ liệu",
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
    @PostMapping("admin/cinemaRoom")
    public ResponseEntity<?> createCinemaWithRoom(@RequestBody CinemaRequest cinema) {
        return ResponseEntity.status(201)
                .body(cinemaService.createCinemaWithRoom(cinema));
    }

    @Operation(
            summary = "Lấy tất cả rạp phim",
            description = "Lấy tất cả rạp phim từ sơ sở dữ liệu",
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
    @GetMapping(value = "/landing/cinemas")
    public ResponseEntity<?> getAll(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size
    ) {
        return ResponseEntity.ok(cinemaService.getAll(page, size));
    }

    @Operation(
            summary = "Lấy thông tin rạp phim",
            description = "Lấy thông tin rạp phim từ sơ sở dữ liệu theo id.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Cinema.class)
                            )
                    )
            }
    )
    @GetMapping(value = "/landing/cinema/{cinemaId}")
    public ResponseEntity<?> getById(@PathVariable("cinemaId") String cinemaId) {
        return ResponseEntity.ok(cinemaService.getById(cinemaId));
    }

    @Operation(
            summary = "Lấy thông tin rạp phim",
            description = "Lấy thông tin rạp phim từ sơ sở dữ liệu theo id, api của admin.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Cinema.class)
                            )
                    )
            }
    )
    @GetMapping(value = "/admin/cinema/{cinemaId}")
    public ResponseEntity<?> getByIdAdmin(@PathVariable("cinemaId") String cinemaId) {
        return ResponseEntity.ok(cinemaService.getByIdInAdmin(cinemaId));
    }

    @Operation(
            summary = "Sửa thông tin rạp phim",
            description = "Sửa thông tin rạp phim từ sơ sở dữ liệu",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @PutMapping("admin/cinema/{cinemaId}")
    public ResponseEntity<?> update(
            @PathVariable("cinemaId") String cinemaId,
            @RequestBody Cinema cinemaRequest
    ) {
        return ResponseEntity.ok(cinemaService.update(cinemaRequest, cinemaId));
    }

}
