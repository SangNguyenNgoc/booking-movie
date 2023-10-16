package sang.se.bookingmovie.app.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sang.se.bookingmovie.auth.AuthRequest;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/auth/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.register(user));
    }

    @PostMapping(value = "/auth/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.authenticate(authRequest));
    }

    @PutMapping(value = "/guest/verify")
    public ResponseEntity<?> verify(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(value = "verify") String verifyCode
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.verify(token, verifyCode));
    }

    @PutMapping(value = "/guest/send-verify")
    public ResponseEntity<?> verify(
            @RequestHeader(value = "Authorization") String token
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.sendEmailToVerify(token));
    }
}
