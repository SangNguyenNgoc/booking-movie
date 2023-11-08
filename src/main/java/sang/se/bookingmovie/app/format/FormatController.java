package sang.se.bookingmovie.app.format;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sang.se.bookingmovie.response.ErrorResponse;
import sang.se.bookingmovie.response.ListResponse;

@RestController
@RequestMapping(value = "/api/v1")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class FormatController {

    private final FormatService formatService;

    @Operation(
            summary = "Api thêm định dạng chiếu phim",
            description = "Tạo định dạng chiếu phim và thêm vào cơ sở dữ liệu",
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
    @PostMapping(value = "/admin/format")
    public ResponseEntity<?> create(@RequestBody Format format) {
        return ResponseEntity.status(201)
                .body(formatService.create(format));
    }


    @Operation(
            summary = "Api lấy định dạng chiếu phim",
            description = "Lấy tất cả định dạng chiếu phim từ sơ sở dữ liệu",
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
    @GetMapping(value = "/admin/formats")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(formatService.getAll());
    }
}
