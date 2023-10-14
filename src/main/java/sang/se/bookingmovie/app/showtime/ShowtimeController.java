package sang.se.bookingmovie.app.showtime;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sang.se.bookingmovie.error.ErrorResponse;

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
    public ResponseEntity<?> createShowtime(@RequestBody ShowtimeRequest showtimeRequest){
        return ResponseEntity.status(201)
                .body(showtimeService.create(showtimeRequest));
    }
}
