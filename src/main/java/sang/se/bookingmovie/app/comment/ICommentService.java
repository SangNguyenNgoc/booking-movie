package sang.se.bookingmovie.app.comment;

import sang.se.bookingmovie.response.ListResponse;

import java.time.LocalDateTime;

public interface ICommentService {

    CommentResponse create(String token, Comment comment);

    ListResponse getAll();

    String setCommentStatus(Integer commentId, String input);

    String deleteComment(String token, Integer commentId);

    ListResponse getCommentByUser(String token);

    ListResponse getCommentByAdmin(String email);

    ListResponse getCommentByStatus(String input);

    void deleteCommentByDate(LocalDateTime currentDate);
}
