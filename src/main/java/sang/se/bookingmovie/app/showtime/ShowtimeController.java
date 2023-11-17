package sang.se.bookingmovie.app.showtime;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sang.se.bookingmovie.app.cinema.CinemaResponse;
import sang.se.bookingmovie.app.movie.MovieResponse;
import sang.se.bookingmovie.error.ErrorResponse;
import sang.se.bookingmovie.response.ListResponse;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class ShowtimeController {

    private final ShowtimeService showtimeService;

    @Operation(
            summary = "Tạo thêm suất chiếu",
            description = "Thêm suất chiếu vào cơ sở dữ liệu.",
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
    @PostMapping("/admin/showtime")
    public ResponseEntity<?> createShowtime(@RequestBody ShowtimeRequest showtimeRequests){
        return ResponseEntity.status(201)
                .body(showtimeService.create(showtimeRequests));
    }

    @Operation(
            summary = "Xóa suất chiếu",
            description = "Xóa suất chiếu khỏi cơ sở dữ liệu.",
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
    @DeleteMapping("/admin/showtime/{showtimeId}")
    public ResponseEntity<?> deleteShowtime(@PathVariable(value = "showtimeId") String showtimeId){
        return ResponseEntity.status(200)
                .body(showtimeService.delete(showtimeId));
    }


    @Operation(
            summary = "Lấy suất chiếu theo ngày và rạp",
            description = "Lấy suất chiếu theo ngày và rạp từ sơ sở dữ liệu",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CinemaResponse.class)
                            )
                    )
            }
    )
    @GetMapping(value = "landing/cinema/{cinemaId}/showtime")
    public ResponseEntity<?> getByCinema(
            @RequestParam("date") Date date,
            @PathVariable("cinemaId") String cinemaId
    ) {
        return ResponseEntity.ok(showtimeService.getShowtimeByCinemaAndDate(date, cinemaId));
    }


    @Operation(
            summary = "Lấy suất chiếu của 1 phim theo ngày",
            description = "Lấy suất chiếu của 1 phim theo ngày từ sơ sở dữ liệu",
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
    @GetMapping(value = "/landing/movie/{movieId}/showtime")
    public ResponseEntity<?> getByMovie(
            @RequestParam("date") Date date,
            @PathVariable("movieId") String movieId
    ) {
        return ResponseEntity.ok(showtimeService.getShowtimeByMovie(date, movieId));
    }

    @Operation(
            summary = "Lấy thời khóa biểu suất chiếu",
            description = "Lấy thời khóa biểu suất chiếu(rạp > phòng > suất chiếu > phim) từ sơ sở dữ liệu, api chỉ dành cho admi.",
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
    @GetMapping(value = "/admin/cinema/room/showtime")
    public ResponseEntity<?> getByCinemaAdmin() {
        return ResponseEntity.ok(showtimeService.getShowtimeByCinemaAdmin());
    }


    @Operation(
            summary = "Api lấy ghế của suất chiếu",
            description = "Lấy ghế của suất chiếu, biến isReserved cho biết ghế đó đã được đặt hay chưa.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ShowtimeResponse.class)
                            )
                    )
            }
    )
    @GetMapping(value = "/landing/showtime/{showtimeId}/seats")
    public ResponseEntity<?> getSeatInShowtime(@PathVariable(value = "showtimeId") String showtimeId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(showtimeService.getSeatInShowTime(showtimeId));
    }

    @Operation(
            summary = "Lấy toàn bộ suất chiếu theo phim ,rạp",
            description = "Lấy toàn bộ suất chiếu theo phim ,rạp từ sơ sở dữ liệu",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CinemaResponse.class)
                            )
                    )
            }
    )

    @GetMapping(value = "/landing/cinema/movie/showtime")
    public ResponseEntity<?> getAllShowtime(){
        return ResponseEntity.ok(showtimeService.getAllCinemaDetailShowtime());
    }

}
