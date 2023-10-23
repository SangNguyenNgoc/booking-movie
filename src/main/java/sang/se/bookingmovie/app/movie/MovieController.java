package sang.se.bookingmovie.app.movie;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotNull;
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
@SecurityRequirement(name = "Bearer Authentication")
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
    @GetMapping(value = "/landing/status/movies")
    public ResponseEntity<?> getMovieByStatus(
            @RequestParam(value = "status", required = false) String slug,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(movieService.getMoviesByStatus(slug, size, page));
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
    @GetMapping(value = "/admin/movies")
    public ResponseEntity<?> getAll(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(movieService.getAll(page, size));
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
    @GetMapping(value = "/admin/movie/{movieId}")
    public ResponseEntity<?> getDetailMovie(@PathVariable(value = "movieId") String movieId) {
        return ResponseEntity.status(200)
                .body(movieService.getMovieById(movieId));
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
                    ),
                    @ApiResponse(
                            description = "Data not found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    @PostMapping(value = "/admin/movie")
    public ResponseEntity<?> create(
            @RequestParam(value = "movie") String movieJson,
            @RequestParam(value = "poster") MultipartFile poster,
            @RequestParam(value = "horPoster") MultipartFile horPoster,
            @RequestParam(value = "images") List<MultipartFile> images
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(movieService.create(movieJson, poster, horPoster, images));
    }


    @Operation(
            summary = "Chỉnh sửa trạng thái của phim",
            description = "API thay đổi trạng thái của phim. " +
                    "API này chỉ dành cho trang admin.",
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
                            description = "Data not found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    @PutMapping(value = "/admin/movie/{movieId}/status/{statusId}")
    public ResponseEntity<?> updateStatus(
            @PathVariable(value = "movieId") String movieId,
            @PathVariable(value = "statusId") Integer statusId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(movieService.updateStatusOfMovie(movieId, statusId));
    }


    @Operation(
            summary = "Chỉnh sửa thuộc tính của phim",
            description = "API thay đổi thuộc tính của phim, API chỉ chỉnh sửa các thuộc tính cơ bản, " +
                    "`genre` và `formats`, không bao gồm hình ảnh và các thuộc tính quan hệ. " +
                    "API này chỉ dành cho trang admin.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Data not found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
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
    @PutMapping(value = "/admin/movie/{movieId}")
    public ResponseEntity<?> updateMovie(
            @PathVariable(value = "movieId") String movieId,
            @RequestBody Movie movie
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(movieService.updateMovie(movieId, movie));
    }


    @Operation(
            summary = "Thay poster mới cho phim",
            description = "API thay đổi poster của phim. " +
                    "API này chỉ dành cho trang admin.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Data not found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    @PutMapping(value = "/admin/movie/{movieId}/poster")
    public ResponseEntity<?> updatePoster(
            @PathVariable(value = "movieId") String movieId,
            @RequestParam(value = "poster") MultipartFile poster
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(movieService.updatePoster(movieId, poster));
    }


    @Operation(
            summary = "Thêm, xóa hình ảnh cho phim",
            description = "API thêm, xóa hình ảnh của phim, `movieId`: id của phim cần thao tác, " +
                    "`images`: mảng hình ảnh muốn thêm vào, " +
                    "`imageIds`: mảng id của hình ảnh muốn xóa. " +
                    "API này chỉ dành cho trang admin.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Data not found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    @PutMapping(value = "/admin/movie/{movieId}/images")
    public ResponseEntity<?> updateImages(
            @PathVariable(value = "movieId") String movieId,
            @RequestParam(value = "images", required = false) List<MultipartFile> images,
            @RequestParam(value = "imageIds", required = false) List<Integer> imageIds
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(movieService.updateImages(movieId, images, imageIds));
    }

    @GetMapping(value = "/landing/searchMovie")
    public ResponseEntity<?> searchMoviesPageToLanding(
            @RequestParam(value = "search") String input,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size
    ) {
        return ResponseEntity.status(200)
                .body(movieService.searchBySlug(input, page, size));
    }

}
