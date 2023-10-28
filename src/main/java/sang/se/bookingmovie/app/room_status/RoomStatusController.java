package sang.se.bookingmovie.app.room_status;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sang.se.bookingmovie.response.ListResponse;

@RestController
@RequestMapping(value = "/api/v1")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class RoomStatusController {
    private final RoomStatusSevice roomStatusSevice;

    @Operation(
            summary = "Lấy trạng thái của phòng chiếu phim",
            description = "Lấy tất cả trạng thái của phòng chiếu phim từ sơ sở dữ liệu",
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
    @GetMapping(value = "/admin/room-status")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(roomStatusSevice.getAll());
    }
}
