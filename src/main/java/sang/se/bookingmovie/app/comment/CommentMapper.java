package sang.se.bookingmovie.app.comment;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.utils.IMapper;
import sang.se.bookingmovie.validate.ObjectsValidator;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class CommentMapper implements IMapper<CommentEntity, Comment, Comment> {

    private final ModelMapper mapper;

    private final ObjectsValidator<Comment> validator;

    @Override
    public CommentEntity requestToEntity(Comment comment) {
        validator.validate(comment);
        return mapper.map(comment, CommentEntity.class);
    }

    @Override
    public Comment entityToResponse(CommentEntity commentEntity) {
        Comment comment = mapper.map(commentEntity, Comment.class);
        comment.setUser(commentEntity.getUser().getFullName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        comment.setCreateDate(commentEntity.getCreateDate().format(formatter));
        return comment;
    }
}
