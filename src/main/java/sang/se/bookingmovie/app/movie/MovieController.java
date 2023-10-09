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
import org.springframework.web.multipart.MultipartFile;
import sang.se.bookingmovie.app.movie_status.MovieStatus;
import sang.se.bookingmovie.app.movie_status.MovieStatusEntity;
import sang.se.bookingmovie.exception.AllException;
import sang.se.bookingmovie.exception.ValidException;
import sang.se.bookingmovie.response.ErrorResponse;
import sang.se.bookingmovie.response.ListResponse;

import java.util.List;

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

    @Operation(
            summary = "Lấy chi tiết 1 phim từ cơ sở dữ liệu bằng slug",
            description = "API lấy chi tiết 1 phim từ cỡ sở dữ liệu băằng slug, API này chỉ dành cho trang admin",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MovieResponse.class)
                            )
                    )
            }
    )
    @GetMapping(value = "/admin/movie")
    public ResponseEntity<?> getDetailMovie(@RequestParam(value = "movie") String slug) {
        return ResponseEntity.status(200)
                .body(movieService.getMovieBySlug(slug));
    }


    @Operation(
            summary = "Thêm 1 phim mới vào cơ sở dữ liệu",
            description = "API thêm 1 phim mới vào cơ sở dữ liệu, khi phim mới được thêm vào `status` mặc định là coming soon. " +
                    "API này chỉ dành cho trang admin",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Invalid",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    @PostMapping(value = "/admin/movie")
    public ResponseEntity<?> create(
            @RequestParam(name = "movie") String movieJson,
            @RequestParam(value = "poster") MultipartFile poster,
            @RequestParam(value = "images") List<MultipartFile> images
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(movieService.create(movieJson, poster, images));
    }

    @PostMapping(value = "/test")
    public ResponseEntity<?> test() {
        throw new ValidException("error", List.of("message"));
    }


}
