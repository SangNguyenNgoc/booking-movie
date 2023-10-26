package sang.se.bookingmovie.app.comment;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping(value = "/comment")
    public ResponseEntity<?> create(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody Comment comment
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.create(token, comment));
    }

    @PutMapping(value = "/deleteComment/{commentId}")
    public ResponseEntity<?> deleteComment(
            @RequestHeader(value = "Authorization") String token,
            @PathVariable(value = "commentId") Integer commentId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.deleteComment(token, commentId));
    }

    @GetMapping(value = "/user/comments")
    public ResponseEntity<?> getCommentByUser(
            @RequestHeader(value = "Authorization") String token
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.getCommentByUser(token));
    }

    @GetMapping(value = "admin/user/comments")
    public ResponseEntity<?> getCommentByUserByAdmin(
            @RequestParam(value = "email") String email
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.getCommentByAdmin(email));
    }

    @PutMapping(value = "/admin/moderationComment/{commentId}")
    public ResponseEntity<?> moderateComment(@PathVariable(value = "commentId") Integer commentId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.moderateComment(commentId));
    }

    @PutMapping(value = "/admin/deleteComment/{commentId}")
    public ResponseEntity<?> deleteCommentByAdmin(@PathVariable(value = "commentId") Integer commentId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.deleteCommentByAdmin(commentId));
    }



}
