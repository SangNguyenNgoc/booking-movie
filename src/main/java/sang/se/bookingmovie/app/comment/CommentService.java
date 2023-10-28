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

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {

    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    private final MovieRepository movieRepository;

    private final JwtService jwtService;

    private final CommentMapper commentMapper;

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
        List<CommentResponse> commentResponseList = userEntity.getComments().stream()
                .sorted(Comparator.comparing(CommentEntity::getCreateDate).reversed())
                .map(commentMapper::entityToResponse)
                .toList();
        return ListResponse.builder()
                .total(commentResponseList.size())
                .data(commentResponseList)
                .build();
    }

    @Override
    public ListResponse getCommentByAdmin(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found", List.of("User is not exits")));
        List<CommentResponse> commentResponseList = userEntity.getComments().stream()
                .sorted(Comparator.comparing(CommentEntity::getCreateDate).reversed())
                .map(commentMapper::entityToResponse)
                .toList();
        return ListResponse.builder()
                .total(commentResponseList.size())
                .data(commentResponseList)
                .build();
    }

    @Override
    public ListResponse getCommentToModerate() {
        List<CommentEntity> commentEntities = commentRepository.findAll();
        List<CommentResponse> commentResponses = commentEntities.stream()
                .sorted(Comparator.comparing(CommentEntity::getCreateDate).reversed())
                .filter(commentEntity -> !commentEntity.getStatus())
                .map(commentMapper::entityToResponse)
                .toList();
        return ListResponse.builder()
                .total(commentEntities.size())
                .data(commentResponses)
                .build();
    }
}
