package sang.se.bookingmovie.statistical;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("api/v1/")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class StatisticalController {

    private final StatisticalService statisticalService;

    @Operation(
            summary = "Lấy thông kê theo tháng năm",
            description = "Lấy thông kê theo doanh thu, vé, rạp, phim theo tháng năm",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CardResponse.class)
                            )
                    )
            }
    )
    @GetMapping(value = "/admin/statistical")
    public ResponseEntity<?> statistical(
            @RequestParam("date") LocalDate date
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(statisticalService.getStatistical(date));
    }


}
