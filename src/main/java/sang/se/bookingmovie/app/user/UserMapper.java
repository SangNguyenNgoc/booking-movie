package sang.se.bookingmovie.app.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.app.movie.Movie;
import sang.se.bookingmovie.auth.AuthRequest;
import sang.se.bookingmovie.utils.IMapper;
import sang.se.bookingmovie.validate.ObjectsValidator;

@Service
@RequiredArgsConstructor
public class UserMapper implements IMapper<UserEntity, User, UserResponse> {

    private final ModelMapper mapper;

    private final ObjectsValidator<User> validator;

    private final ObjectsValidator<AuthRequest> authValidator;

    private final ObjectsValidator<UserUpdate> updateValidator;

    @Override
    public UserEntity requestToEntity(User user) {
        validator.validate(user);
        return mapper.map(user, UserEntity.class);
    }

    @Override
    public UserResponse entityToResponse(UserEntity userEntity) {
        UserResponse userResponse = mapper.map(userEntity, UserResponse.class);
        userResponse.setRole(userEntity.getRole().getName());
        return userResponse;
    }

    public void validateAuth(AuthRequest authRequest) {
        authValidator.validate(authRequest);
    }

    public void validateUpdate(UserUpdate userUpdate) {
        updateValidator.validate(userUpdate);
    }
}
