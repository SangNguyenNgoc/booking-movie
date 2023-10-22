package sang.se.bookingmovie.app.showtime;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sang.se.bookingmovie.error.ErrorResponse;
import sang.se.bookingmovie.response.ListResponse;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class ShowtimeController {

    private final ShowtimeService showtimeService;

    @Operation(
            description = "Thêm suất chiếu ghê ",
            summary = "Api tạo thêm suất chiếu",
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
    public ResponseEntity<?> createShowtime(@RequestBody List<ShowtimeRequest> showtimeRequests){
        return ResponseEntity.status(201)
                .body(showtimeService.create(showtimeRequests));
    }


    @Operation(
            description = "Lấy suất chiếu theo ngày và rạp từ sơ sở dữ liệu",
            summary = "Api lấy suất chiếu theo ngày và rạp",
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
    @GetMapping(value = "landing/cinema/{cinemaId}/showtime")
    public ResponseEntity<?> getByCinema(
            @RequestParam("date") Date date,
            @PathVariable("cinemaId") String cinemaId
    ) {
        return ResponseEntity.ok(showtimeService.getShowtimeByCinemaAndDate(date, cinemaId));
    }


    @Operation(
            description = "Lấy suất chiếu cua phim theo ngày từ sơ sở dữ liệu",
            summary = "Api lấy suất chiếu cua phim  theo ngày",
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
    @GetMapping(value = "/landing/movie/{movieId}/showtime")
    public ResponseEntity<?> getByMovie(
            @RequestParam("date") Date date,
            @PathVariable("movieId") String movieId
    ) {
        return ResponseEntity.ok(showtimeService.getShowtimeByMovie(date, movieId));
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

}
