package sang.se.bookingmovie.app.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sang.se.bookingmovie.auth.AuthRequest;
import sang.se.bookingmovie.auth.AuthResponse;
import sang.se.bookingmovie.response.ErrorResponse;
import sang.se.bookingmovie.response.ListResponse;

import java.sql.Date;

@RestController
@RequestMapping(value = "/api/v1")
@SecurityRequirement(name = "Bearer Authentication")
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
            description = "API đăng nhập vào tài khoản người dùng. Đăng nhập thành công sẽ trả token.",
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
    @PostMapping(value = "/auth/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.authenticate(authRequest));
    }

    @Operation(
            summary = "Gửi email để xác minh tài khoản",
            description = "API gửi mã xác minh tài khoản người dùng qua email. " +
                    "API chỉ dành cho tài khoản chưa xác minh.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            description = "Forbidden",
                            responseCode = "403",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @GetMapping(value = "/guest/sendToVerifyAccount")
    public ResponseEntity<?> sendToVerifyAccount(
            @RequestHeader(value = "Authorization") String token
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.sendToVerifyAccount(token));
    }


    @Operation(
            summary = "Xác minh tài khoản",
            description = "API xác minh tài khoản người dùng. " +
                    "Cần mã vừa gửi qua email để xác minh tài khoản. " +
                    "API chỉ dành cho tài khoản chưa xác minh.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            description = "Forbidden",
                            responseCode = "403",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @PutMapping(value = "/guest/verifyAccount")
    public ResponseEntity<?> verifyAccount(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody VerifyRequest verify
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.verify(token, verify));
    }


    @Operation(
            summary = "Gửi email để xác nhận đổi email",
            description = "API gửi mã xác nhận để đổi email người dùng. " +
                    "Cần email mới để gửi mã xác nhận đổi email. " +
                    "API chỉ dành cho tài khoản đã xác minh.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            description = "Forbidden",
                            responseCode = "403",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @GetMapping(value = "/customer/sendToUpdateMail")
    public ResponseEntity<?> sendToUpdateMail(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(value = "email") String newMail
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.sendToUpdateEmail(token, newMail));
    }


    @Operation(
            summary = "Lấy thông tin của người dùng",
            description = "API lấy thông tin của người dùng đang đăng nhập. ",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            description = "Forbidden",
                            responseCode = "403",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @GetMapping(value = "/profile")
    public ResponseEntity<?> getProfile(
            @RequestHeader(value = "Authorization") String token
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getCurrentUser(token, null));
    }


    @Operation(
            summary = "Lấy thông tin của người dùng",
            description = "API lấy thông tin của người dùng dựa vào email. " +
                    "Cần email để tìm thông tin của người dùng đó. " +
                    "API này chỉ dành cho trang admin. ",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            description = "Forbidden",
                            responseCode = "403",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            description = "Not found",
                            responseCode = "404",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @GetMapping(value = "/admin/user/{userId}")
    public ResponseEntity<?> getCurrentUserAdmin(
            @PathVariable(value = "userId") String userId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getCurrentUser(null, userId));
    }


    @Operation(
            summary = "Lấy thông tin của tất cả người dùng",
            description = "API lấy thông tin của tất cả người dùng. " +
                    "API này chỉ dành cho trang admin. ",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ListResponse.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            description = "Forbidden",
                            responseCode = "403",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @GetMapping(value = "/admin/users")
    public ResponseEntity<?> getAll(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getAll(page, size));
    }


    @Operation(
            summary = "Kiểm tra mật khẩu trước khi đổi",
            description = "API kiểm tra mật khâủ trước khi đổi. ",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Boolean.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            description = "Forbidden",
                            responseCode = "403",
                            content = @Content(mediaType = "application/json")
                    ),

            }
    )
    @GetMapping(value = "/checkPassword")
    public ResponseEntity<?> checkPassword(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(value = "password") String password
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.checkPassword(token, password));
    }


    @Operation(
            summary = "Đổi mật khẩu.",
            description = "API đôỉ mật khẩu. " +
                    "Cần truyền mật khẩu mới và cũ.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            description = "Forbidden",
                            responseCode = "403",
                            content = @Content(mediaType = "application/json")
                    ),

            }
    )
    @PutMapping(value = "/changePassword")
    public ResponseEntity<?> changePassword(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody ChangePassRequest verifyRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.changePassword(token, verifyRequest));
    }


    @Operation(
            summary = "Gửi mã xác nhận qua mail khi quên mật khẩu",
            description = "API gửi mã xác nhận qua mail khi quên mật khẩu. " +
                    "Cần truyền email đã được đăng kí.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            description = "Conflict",
                            responseCode = "409",
                            content = @Content(mediaType = "application/json")
                    ),


            }
    )
    @GetMapping(value = "/auth/sendToResetPass")
    public ResponseEntity<?> sendToResetPass(
            @RequestParam(value = "email") String email
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.sendToResetPassword(email));
    }


    @Operation(
            summary = "Đặt lại mật khẩu",
            description = "API đặt lại mật khẩu. " +
                    "Cần truyền những thông tin cần thiết trong body.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            description = "Conflict",
                            responseCode = "409",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            description = "Not found",
                            responseCode = "404",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "409",
                            content = @Content(mediaType = "application/json")
                    ),


            }
    )
    @PutMapping(value = "/auth/resetPass")
    public ResponseEntity<?> sendToRetPass(
            @RequestBody ResetPassRequest verify
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.resetPassword(verify));
    }

    @Operation(
            summary = "Kiểm tra đường link đặt lại mật khẩu",
            description = "API kiểm tra đường link đặt lại mật khẩu. " +
                    "Truyền tham số `verify` của url để tiến hành kiểm tra.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            description = "Conflict",
                            responseCode = "409",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            description = "Not found",
                            responseCode = "404",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "409",
                            content = @Content(mediaType = "application/json")
                    ),


            }
    )
    @GetMapping(value = "/auth/checkUrlToResetPass")
    public ResponseEntity<?> checkUrlToResetPass(
            @RequestParam(value = "verify") String verifyToken
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.checkUrlToReset(verifyToken));
    }


    @Operation(
            summary = "Cập nhật thông tin ngươì dùng",
            description = "API cập nhật thông tin ngươì dùng, các trường có thể cập nhật gồm: " +
                    "tên, ngày sinh, giới tính, số điện thoại. ",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class)
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
    @PutMapping(value = "/user")
    public ResponseEntity<?> updateUser(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody UserUpdate userUpdate
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.updateUser(token, userUpdate));
    }

    @Operation(
            summary = "Cập nhật avatar ngươì dùng",
            description = "API cập nhật avatar ngươì dùng, truyền avatar để thay đổi ảnh, nếu không truyền avatar tự động " +
                    "quay về ảnh mặc định (coi như là xóa ảnh). ",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class)
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
    @PutMapping(value = "/user/avatar")
    public ResponseEntity<?> updateAvatar(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(value = "avatar") MultipartFile avatar
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.updateAvatar(token, avatar));
    }

}
