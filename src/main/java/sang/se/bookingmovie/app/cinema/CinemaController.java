package sang.se.bookingmovie.app.cinema;

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
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CinemaController {
    private final CinemaService cinemaService;

    @Operation(
            description = "Tạo rạp phim và thêm vào cơ sở dữ liệu",
            summary = "Api thêm rạp phim",
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
    public ResponseEntity<?> create(@RequestBody Cinema cinema){
        return ResponseEntity.status(201)
                .body(cinemaService.create(cinema));
    }

    @Operation(
            description = "Lấy tất cả rạp phim từ sơ sở dữ liệu",
            summary = "Api lấy tất cả rạp phim",
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
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(cinemaService.getAll());
    }

    @Operation(
            description = "Lấy thông tin rạp phim từ sơ sở dữ liệu",
            summary = "Api lấy thông tin rạp phim",
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
    @GetMapping(value = "/landing/cinema/{cinemaId}")
    public ResponseEntity<?> getById(@PathVariable("cinemaId") String cinemaId) {
        return ResponseEntity.ok(cinemaService.getById(cinemaId));
    }

    @Operation(
            description = "Sửa thông tin rạp phim từ sơ sở dữ liệu",
            summary = "Api sửa thông tin rạp phim",
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
    @PatchMapping("admin/cinema/{cinemaId}")
    public ResponseEntity<?> update(@PathVariable("cinemaId") String cinemaId, @RequestBody Cinema cinemaRequest){
        return ResponseEntity.ok(cinemaService.update(cinemaRequest, cinemaId));
    }
//    @Operation(
//            description = "Lấy thông tin rạp phim có điều kiện từ sơ sở dữ liệu",
//            summary = "Api lấy thông tin rạp phim có điều kiện",
//            responses = {
//                    @ApiResponse(
//                            description = "Success",
//                            responseCode = "200",
//                            content = @Content(
//                                    mediaType = "application/json",
//                                    schema = @Schema(implementation = ListResponse.class)
//                            )
//                    )
//            }
//    )
//    @GetMapping(value = "/cinemas")
//    public ResponseEntity<?> getByField(@RequestParam("search") String search, @RequestParam()) {
//        return ResponseEntity.ok("");
//    }
}
