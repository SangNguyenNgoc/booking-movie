package sang.se.bookingmovie.app.bill;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.sql.Date;

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


    @PutMapping(value = "/customer/payment/{transactionId}")
    public ResponseEntity<?> pay(
            @PathVariable(value = "transactionId") String transactionId
    ) throws ServletException, JSONException, IOException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(billService.pay(transactionId));
    }


    @PutMapping(value = "/customer/refund/{billId}")
    public ResponseEntity<?> refund(
            @PathVariable(value = "billId") String billId,
            @RequestBody String reason
    ) throws ServletException, JSONException, IOException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(billService.refund(billId, reason));
    }


    @GetMapping(value = "/customer/user/bills")
    public ResponseEntity<?> getBillByUser(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "date", required = false) Date date
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(billService.getBillByUser(token, page, size, date));
    }


    @GetMapping(value = "/admin/user/bills")
    public ResponseEntity<?> getBillByAdmin(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "date", required = false) Date date
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(billService.getBillByAdmin(email, page, size, date));
    }

    @GetMapping(value = "/customer/bill/{billId}")
    public ResponseEntity<?> getBillByUser(
            @PathVariable(value = "billId") String billId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(billService.getDetail(billId));
    }
}
