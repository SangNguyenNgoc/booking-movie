package sang.se.bookingmovie.app.comment;

import sang.se.bookingmovie.response.ListResponse;

import java.util.List;

public interface ICommentService {

    String create(String token, Comment comment);

    String moderateComment(Integer commentId);

    String deleteCommentByAdmin(Integer commentId);

    String deleteComment(String token, Integer commentId);

    ListResponse getCommentByUser(String token);

    ListResponse getCommentByAdmin(String email);
}
