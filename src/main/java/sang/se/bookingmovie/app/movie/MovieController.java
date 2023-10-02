package sang.se.bookingmovie.app.movie;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
            description = "API lấy phim theo trạng thái, đường dẫn API này chỉ lấy các thuộc tính cơ bản.",
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
    @GetMapping(value = "/movies")
    public ResponseEntity<?> getMovieByStatus(@RequestParam("status") String slug) {
        return ResponseEntity.status(200)
                .body(movieService.getMoviesByStatus(slug));
    }

    @Operation(
            summary = "Lấy phim theo tất cả trạng thái từ sơ sở dữ liệu",
            description = "API lấy phim theo trạng thái, đường dẫn API này chỉ lấy các thuộc tính cơ bản.",
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
    @GetMapping(value = "/movies/status")
    public ResponseEntity<?> getMovieByStatus() {
        return ResponseEntity.status(200)
                .body(movieService.getMoviesByStatus());
    }
}
