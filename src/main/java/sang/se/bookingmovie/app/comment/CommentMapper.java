package sang.se.bookingmovie.app.comment;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.utils.IMapper;

@Service
@RequiredArgsConstructor
public class CommentMapper implements IMapper<CommentEntity, Comment, Comment> {
    @Override
    public CommentEntity requestToEntity(Comment comment) {
        return null;
    }

    @Override
    public Comment entityToResponse(CommentEntity commentEntity) {
        return null;
    }
}
