package sang.se.bookingmovie.app.movie_genre;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sang.se.bookingmovie.error.ErrorResponse;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class MovieGenreController {

    private final MovieGenreService movieGenreService;

    @Operation(
            description = "Tạo thể loại phim và thêm vào cơ sở dữ liệu",
            summary = "Api thêm thể loại phim",
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
    public ResponseEntity<?> create(@RequestBody MovieGenre movieGenre) {
        return ResponseEntity.status(201)
                .body(movieGenreService.create(movieGenre));
    }


    @Operation(
            description = "Lấy tất cả thể loại phim từ sơ sở dữ liệu",
            summary = "Api lấy thể loại phim",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MovieGenre.class)
                            )
                    )
            }
    )
    @GetMapping(value = "/movie-genres")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(movieGenreService.getAll());
    }


}
