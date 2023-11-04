package sang.se.bookingmovie.statistical;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sang.se.bookingmovie.response.ListResponse;

import java.time.LocalDate;

@RestController
@RequestMapping("api/v1/")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class StatisticalController {

    private final StatisticalService statisticalService;

    @Operation(
            summary = "Lấy thông kê doanh thu",
            description = "Lấy thông kê doanh thu theo tháng của hệ thống và daonh thu từng ngày trong tháng",
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
    @GetMapping(value = "/admin/statistical/revenue")
    public ResponseEntity<?> revenue(
            @RequestParam("date") LocalDate date
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(statisticalService.getRevenue(date));
    }

    @Operation(
            summary = "Lấy thông kê vé",
            description = "Lấy thông kê số vé theo tháng của hệ thống và số vé từng ngày trong tháng",
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
    @GetMapping(value = "/admin/statistical/ticket")
    public ResponseEntity<?> totalTicket(
            @RequestParam("date") LocalDate date
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(statisticalService.getTotalTicket(date));
    }

    @Operation(
            summary = "Lấy thông kê doanh thu rạp",
            description = "Lấy thông kê doanh thu của rạp cao nhất theo tháng và doanh thu từng rạp trong tháng",
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
    @GetMapping(value = "/admin/statistical/cinema")
    public ResponseEntity<?> cinemaRevenue(
            @RequestParam("month") int month,
            @RequestParam ("year") int year
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(statisticalService.getRevenueCinema(month,year));
    }

    @Operation(
            summary = "Lấy thông kê doanh thu phim",
            description = "Lấy thông kê doanh thu của phim cao nhất và doanh thu từng phim trong tháng",
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
    @GetMapping(value = "/admin/statistical/movie")
    public ResponseEntity<?> movieRevenue(
            @RequestParam("month") int month,
            @RequestParam ("year") int year
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(statisticalService.getRevenueMovie(month,year));
    }
}
