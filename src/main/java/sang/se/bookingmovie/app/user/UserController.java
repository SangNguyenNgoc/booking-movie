package sang.se.bookingmovie.app.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sang.se.bookingmovie.auth.AuthRequest;
import sang.se.bookingmovie.auth.AuthResponse;
import sang.se.bookingmovie.response.ErrorResponse;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Đăng ký tài khoản",
            description = "API đăng ký tài khoản người dùng, chưa bao gồm gửi mail xác nhận. " +
                    "Đăng kí thành công sẽ trả token.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthResponse.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Conflict",
                            responseCode = "409",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    @PostMapping(value = "/auth/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.register(user));
    }


    @Operation(
            summary = "Đăng nhập tài khoản",
            description = "API đăng nhập vào tài khoản người dùng. Đăng nhập thành công sẽ có token trả về.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthResponse.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Conflict",
                            responseCode = "409",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    @PostMapping(value = "/auth/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.authenticate(authRequest));
    }

    @Operation(
            summary = "Xác minh tài khoản",
            description = "API gưửi xác minh tài khoản người dùng. " +
                    "Cần token của người dùng để tìm email và gửi mã. " +
                    "API chỉ dành cho tài khoản chưa xác minh.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            description = "Forbidden",
                            responseCode = "403",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @PutMapping(value = "/guest/sendToVerifyAccount")
    public ResponseEntity<?> sendToVerifyAccount(
            @RequestHeader(value = "Authorization") String token
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.sendToVerifyAccount(token));
    }


    @Operation(
            summary = "Xác minh tài khoản",
            description = "API xác minh tài khoản người dùng. " +
                    "Cần token của người dùng và mã vừa gửi qua email để xác minh tài khoản. " +
                    "API chỉ dành cho tài khoản chưa xác minh.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthResponse.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            description = "Forbidden",
                            responseCode = "403",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @PutMapping(value = "/guest/verifyAccount")
    public ResponseEntity<?> verifyAccount(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(value = "verify") String verifyAccount
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.verify(token, verifyAccount));
    }


    @PutMapping(value = "/guest/sendToUpdateMail")
    public ResponseEntity<?> sendToUpdateMail(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(value = "email") String newMail
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.sendToUpdateEmail(token, newMail));
    }


    @PutMapping(value = "/guest/updateMail")
    public ResponseEntity<?> updateMail(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(value = "verify") String verifyMail
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.updateEmail(token, verifyMail));
    }


    @GetMapping(value = "/guest/currentUser")
    public ResponseEntity<?> getCurrentUser(
            @RequestHeader(value = "Authorization") String token
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getCurrentUser(token, null));
    }


    @GetMapping(value = "/admin/user")
    public ResponseEntity<?> getCurrentUserAdmin(
            @RequestParam(value = "email") String email
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getCurrentUser(null, email));
    }


    @GetMapping(value = "/admin/users")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getAll());
    }



}
