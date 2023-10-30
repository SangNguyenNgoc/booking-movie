package sang.se.bookingmovie.app.comment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sang.se.bookingmovie.error.ErrorResponse;
import sang.se.bookingmovie.response.ListResponse;

@RestController
@RequestMapping(value = "/api/v1")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(
            summary = "Đăng comment",
            description = "Đăng comment vào 1 phim bất kì.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
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
                    ),
                    @ApiResponse(
                            description = "Data not found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    @PostMapping(value = "/comment")
    public ResponseEntity<?> create(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody Comment comment
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.create(token, comment));
    }

    @Operation(
            summary = "Xóa comment",
            description = "Xóa comment của người dùng, chỉ được thực hiện bởi chính chủ.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
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
                    ),
                    @ApiResponse(
                            description = "Data not found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Access denied",
                            responseCode = "403",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
            }
    )
    @PutMapping(value = "/deleteComment/{commentId}")
    public ResponseEntity<?> deleteComment(
            @RequestHeader(value = "Authorization") String token,
            @PathVariable(value = "commentId") Integer commentId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.deleteComment(token, commentId));
    }


    @Operation(
            summary = "Lấy lịch sử comment của người dùng",
            description = "Lấy lịch sử comment của người dùng, chỉ được thực hiện bởi chính chủ.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            description = "Data not found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    @GetMapping(value = "/user/comments")
    public ResponseEntity<?> getCommentByUser(
            @RequestHeader(value = "Authorization") String token
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.getCommentByUser(token));
    }


    @Operation(
            summary = "Lấy lịch sử comment của 1 người dùng",
            description = "Lấy lịch sử comment của 1 người dụng bằng email, chỉ được thực hiện bởi admin.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            description = "Data not found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    @GetMapping(value = "admin/user/{userId}/comments")
    public ResponseEntity<?> getCommentByUserByAdmin(
            @PathVariable(value = "userId") String userId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.getCommentByAdmin(userId));
    }


    @Operation(
            summary = "Duyệt 1 comment",
            description = "API duyệt comment, chỉ được thực hiện bởi admin.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            description = "Data not found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    @PutMapping(value = "/admin/moderationComment/{commentId}")
    public ResponseEntity<?> moderateComment(@PathVariable(value = "commentId") Integer commentId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.moderateComment(commentId));
    }


    @Operation(
            summary = "Xóa 1 comment",
            description = "API xóa comment, chỉ được thực hiện bởi admin.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            description = "Data not found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    @PutMapping(value = "/admin/deleteComment/{commentId}")
    public ResponseEntity<?> deleteCommentByAdmin(@PathVariable(value = "commentId") Integer commentId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.deleteCommentByAdmin(commentId));
    }


    @Operation(
            summary = "Lấy comment chưa được duyệt",
            description = "API lấy comment chưa được duyệt, chỉ được thực hiện bởi admin.",
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
    @GetMapping(value = "admin/comments")
    public ResponseEntity<?> getCommentToModerate() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.getCommentToModerate());
    }

}
