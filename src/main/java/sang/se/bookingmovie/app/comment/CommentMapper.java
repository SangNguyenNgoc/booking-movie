package sang.se.bookingmovie.app.comment;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.utils.IMapper;
import sang.se.bookingmovie.validate.ObjectsValidator;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class CommentMapper implements IMapper<CommentEntity, Comment, CommentResponse> {

    private final ModelMapper mapper;

    private final ObjectsValidator<Comment> validator;

    @Override
    public CommentEntity requestToEntity(Comment comment) {
        validator.validate(comment);
        return mapper.map(comment, CommentEntity.class);
    }

    @Override
    public CommentResponse entityToResponse(CommentEntity commentEntity) {
        CommentResponse commentResponse = mapper.map(commentEntity, CommentResponse.class);
        commentResponse.setUser(commentEntity.getUser().getFullName());
        commentResponse.setAvatarUser(commentEntity.getUser().getAvatar());
        commentResponse.setMovie(commentEntity.getMovie().getSubName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        commentResponse.setCreateDate(commentEntity.getCreateDate().format(formatter));
        return commentResponse;
    }
}
