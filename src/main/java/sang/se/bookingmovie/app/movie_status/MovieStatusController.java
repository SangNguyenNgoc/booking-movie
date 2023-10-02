package sang.se.bookingmovie.app.movie_status;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sang.se.bookingmovie.response.ErrorResponse;
import sang.se.bookingmovie.response.ListResponse;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class MovieStatusController {

    private final MovieStatusService movieStatusService;

    @Operation(
            summary = "Api thêm trạng thái phim",
            description = "Tạo trạng thái phim và thêm vào cơ sở dữ liệu",
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
    @PostMapping(value = "/movie-status")
    public ResponseEntity<?> create(@RequestBody MovieStatus movieStatus) {
        return ResponseEntity.status(201)
                .body(movieStatusService.create(movieStatus));
    }


    @Operation(
            summary = "Api lấy trạng thái phim",
            description = "Lấy tất cả trạng thái phim từ sơ sở dữ liệu",
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
    @GetMapping(value = "/movie-status")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(movieStatusService.getAll());
    }
}
