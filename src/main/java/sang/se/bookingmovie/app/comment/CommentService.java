package sang.se.bookingmovie.app.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sang.se.bookingmovie.app.movie.MovieEntity;
import sang.se.bookingmovie.app.movie.MovieRepository;
import sang.se.bookingmovie.app.user.UserEntity;
import sang.se.bookingmovie.app.user.UserRepository;
import sang.se.bookingmovie.exception.AllException;
import sang.se.bookingmovie.exception.DataNotFoundException;
import sang.se.bookingmovie.exception.UserNotFoundException;
import sang.se.bookingmovie.response.ListResponse;
import sang.se.bookingmovie.utils.ApplicationUtil;
import sang.se.bookingmovie.utils.JwtService;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {

    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    private final MovieRepository movieRepository;

    private final JwtService jwtService;

    private final CommentMapper commentMapper;

    private final ApplicationUtil applicationUtil;

    @Override
    public String create(String token, Comment comment) {
        String userId = jwtService.validateToken(token);
        UserEntity userEntity = userRepository.findById(jwtService.extractSubject(userId))
                .orElseThrow(() -> new UserNotFoundException("User not found", List.of("User is not exits")));
        MovieEntity movieEntity = movieRepository.findById(comment.getMovieId())
                .orElseThrow(() -> new DataNotFoundException("Data not found", List.of("Movie is not exits")));
        CommentEntity commentEntity = commentMapper.requestToEntity(comment);
        commentEntity.setStatus(false);
        commentEntity.setUser(userEntity);
        commentEntity.setMovie(movieEntity);
        commentEntity.setCreateDate(LocalDateTime.now());
        commentRepository.save(commentEntity);
        return "Success";
    }

    @Override
    @Transactional
    public String moderateComment(Integer commentId) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new DataNotFoundException("Data not found", List.of("Comment is not exits")));
        comment.setStatus(true);
        return "Success";
    }

    @Override
    public String deleteCommentByAdmin(Integer commentId) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new DataNotFoundException("Data not found", List.of("Comment is not exits")));
        commentRepository.delete(comment);
        return "Success";
    }

    @Override
    public String deleteComment(String token, Integer commentId) {
        String userId = jwtService.validateToken(token);
        UserEntity userEntity = userRepository.findById(jwtService.extractSubject(userId))
                .orElseThrow(() -> new UserNotFoundException("User not found", List.of("User is not exits")));
        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() -> new DataNotFoundException("Data not found", List.of("Comment is not exits")));
        if(commentEntity.getUser().getId().equals(userEntity.getId())) {
            commentRepository.delete(commentEntity);
        } else {
            throw new AllException("Access denied", 403, List.of("This comment is not yours"));
        }
        return "Success";
    }

    @Override
    public ListResponse getCommentByUser(String token) {
        String userId = jwtService.validateToken(token);
        UserEntity userEntity = userRepository.findById(jwtService.extractSubject(userId))
                .orElseThrow(() -> new UserNotFoundException("User not found", List.of("User is not exits")));
        List<Comment> commentList = userEntity.getComments().stream()
                .map(commentMapper::entityToResponse)
                .toList();
        return ListResponse.builder()
                .total(commentList.size())
                .data(commentList)
                .build();
    }

    @Override
    public ListResponse getCommentByAdmin(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found", List.of("User is not exits")));
        List<Comment> commentList = userEntity.getComments().stream()
                .map(commentMapper::entityToResponse)
                .toList();
        return ListResponse.builder()
                .total(commentList.size())
                .data(commentList)
                .build();
    }
}
