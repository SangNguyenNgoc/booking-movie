package sang.se.bookingmovie.app.movie;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sang.se.bookingmovie.app.movie_status.MovieStatus;
import sang.se.bookingmovie.app.movie_status.MovieStatusEntity;
import sang.se.bookingmovie.response.ListResponse;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @Operation(
            summary = "Lấy phim theo 1 trạng thái từ sơ sở dữ liệu",
            description = "API lấy phim theo trạng thái, nếu truyền param `status`, 1 status kèm phim sẽ được trả, " +
                    "nếu không truyền param, tất cả status kèm phim sẽ được trả. " +
                    "Đường dẫn API này chỉ lấy các thuộc tính cơ bản, API này dành cho trang người dùng.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MovieStatus.class)
                            )
                    )
            }
    )
    @GetMapping(value = "/status/movies")
    public ResponseEntity<?> getMovieByStatus(
            @RequestParam(value = "status", required = false) String slug
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(movieService.getMoviesByStatus(slug));
    }

    @Operation(
            summary = "Lấy danh sách phim từ cơ sở dữ liệu",
            description = "API lấy danh sách phim từ cỡ sở dữ liệu, API này chỉ dành cho trang admin",
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
    @GetMapping(value = "admin/movies")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(movieService.getAll());
    }

    @PostMapping(value = "admin/movie")
    public ResponseEntity<?> create() {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(null);
    }
}
