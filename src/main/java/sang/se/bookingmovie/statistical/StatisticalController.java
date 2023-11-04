package sang.se.bookingmovie.statistical;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

    @GetMapping(value = "/landing/statistical/test")
    public ResponseEntity<?> test(
            @RequestParam("date") LocalDate date
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(statisticalService.getTotalTicket(date));
    }

    @GetMapping(value = "/landing/statistical/cinema")
    public ResponseEntity<?> cinema(
            @RequestParam("date") LocalDate date
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(statisticalService.getRevenueCinema(date));
    }

    @GetMapping(value = "/landing/statistical/movie")
    public ResponseEntity<?> movie(
            @RequestParam("month") int month,
            @RequestParam ("year") int year
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(statisticalService.getRevenueMovie(month,year));
    }
}
