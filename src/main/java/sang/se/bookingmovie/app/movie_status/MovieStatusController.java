package sang.se.bookingmovie.app.movie_status;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sang.se.bookingmovie.app.movie_genre.MovieGenre;
import sang.se.bookingmovie.app.movie_genre.MovieGenreService;
import sang.se.bookingmovie.error.ErrorResponse;
import sang.se.bookingmovie.response.ListResponse;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class MovieStatusController {

    private final MovieStatusService movieStatusService;

    @Operation(
            description = "Tạo trạng thái phim và thêm vào cơ sở dữ liệu",
            summary = "Api thêm trạng thái phim",
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
    @PostMapping(value = "/movie-genre")
    public ResponseEntity<?> create(@RequestBody MovieStatus movieStatus) {
        return ResponseEntity.status(201)
                .body(movieStatusService.create(movieStatus));
    }


    @Operation(
            description = "Lấy tất cả trạng thái phim từ sơ sở dữ liệu",
            summary = "Api lấy trạng thái phim",
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
    @GetMapping(value = "/movie-genres")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(movieStatusService.getAll());
    }
}
