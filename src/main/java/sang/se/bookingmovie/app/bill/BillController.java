package sang.se.bookingmovie.app.bill;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/v1")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;

    @PostMapping(value = "/customer/bill")
    public ResponseEntity<?> create(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody Bill bill
    ) throws UnsupportedEncodingException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(billService.create(token, bill));
    }
}
