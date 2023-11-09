package sang.se.bookingmovie.app.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sang.se.bookingmovie.app.movie.MovieEntity;
import sang.se.bookingmovie.app.movie.MovieRepository;
import sang.se.bookingmovie.app.user.Gender;
import sang.se.bookingmovie.app.user.UserEntity;
import sang.se.bookingmovie.app.user.UserRepository;
import sang.se.bookingmovie.exception.AllException;
import sang.se.bookingmovie.exception.DataNotFoundException;
import sang.se.bookingmovie.exception.UserNotFoundException;
import sang.se.bookingmovie.response.ListResponse;
import sang.se.bookingmovie.utils.ApplicationUtil;
import sang.se.bookingmovie.utils.JwtService;

import java.time.LocalDate;
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

    private final ApplicationUtil applicationUtil;

    @Override
    public String create(String token, Comment comment) {
        String userId = jwtService.extractSubject(jwtService.validateToken(token));
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Conflict", List.of("User is not exits")));
        MovieEntity movieEntity = movieRepository.findById(comment.getMovieId())
                .orElseThrow(() -> new DataNotFoundException("Data not found", List.of("Movie is not exits")));
        if(commentRepository.hasUserCommented(userId, comment.getMovieId())) {
            throw new AllException("Bad request", 400, List.of("User has commented"));
        }
        CommentEntity commentEntity = CommentEntity.builder()
                .content(comment.getContent())
                .rating(comment.getRating())
                .status(CommentStatus.PENDING)
                .user(userEntity)
                .movie(movieEntity)
                .createDate(LocalDateTime.now())
                .build();
        commentRepository.save(commentEntity);
        setRatingInMovie(movieEntity, comment.getRating());
        return "Success";
    }

    @Transactional
    public void setRatingInMovie(MovieEntity movieEntity, Integer rating) {
        movieEntity.setSumOfRatings(movieEntity.getSumOfRatings() + 1);
        movieEntity.setNumberOfRatings(movieEntity.getNumberOfRatings() + rating);
    }

    @Override
    public ListResponse getAll() {
        List<CommentEntity> commentEntities = commentRepository.findAll();
        return ListResponse.builder()
                .total(commentEntities.size())
                .data(commentEntities.stream().map(commentMapper::entityToResponse).collect(Collectors.toList()))
                .build();
    }

    @Override
    @Transactional
    public String setCommentStatus(Integer commentId, String input) {
        String status = applicationUtil.toSlug(input);
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new DataNotFoundException("Data not found", List.of("Comment is not exits")));
        switch (status) {
            case "approved" -> comment.setStatus(CommentStatus.APPROVED);
            case "pending" -> comment.setStatus(CommentStatus.PENDING);
            case "deleted" -> comment.setStatus(CommentStatus.DELETED);
            default -> throw new AllException("Data invalid", 404, List.of("Comment status invalid"));
        }
        return "Success";
    }


    @Override
    public String deleteComment(String token, Integer commentId) {
        String userId = jwtService.validateToken(token);
        UserEntity userEntity = userRepository.findById(jwtService.extractSubject(userId))
                .orElseThrow(() -> new UserNotFoundException("Conflict", List.of("User is not exits")));
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
                .orElseThrow(() -> new UserNotFoundException("Conflict", List.of("User is not exits")));
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
    public ListResponse getCommentByAdmin(String userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Conflict", List.of("User is not exits")));
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
    public ListResponse getCommentByStatus(String input) {
        String status = input.toUpperCase();
        List<CommentEntity> commentEntities = commentRepository.findAll();
        List<CommentResponse> commentResponses = commentEntities.stream()
                .sorted(Comparator.comparing(CommentEntity::getCreateDate).reversed())
                .filter(commentEntity -> commentEntity.getStatus().toString().equals(status))
                .map(commentMapper::entityToResponse)
                .toList();
        return ListResponse.builder()
                .total(commentResponses.size())
                .data(commentResponses)
                .build();
    }

    @Override
    public void deleteCommentByDate(LocalDateTime currentDate) {
        LocalDateTime thirtyDaysAgo = currentDate.minusDays(30);
        List<CommentEntity> commentEntities = commentRepository.findAll();
        commentEntities.forEach(commentEntity -> {
            if(commentEntity.getCreateDate().isBefore(thirtyDaysAgo)) {
                commentRepository.delete(commentEntity);
            }
        });
    }
}
