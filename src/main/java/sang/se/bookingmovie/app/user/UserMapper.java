package sang.se.bookingmovie.app.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.app.movie.Movie;
import sang.se.bookingmovie.auth.AuthRequest;
import sang.se.bookingmovie.exception.AllException;
import sang.se.bookingmovie.utils.ApplicationUtil;
import sang.se.bookingmovie.utils.IMapper;
import sang.se.bookingmovie.validate.ObjectsValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMapper implements IMapper<UserEntity, User, UserResponse> {

    private final ModelMapper mapper;

    private final ObjectsValidator<User> validator;

    private final ObjectsValidator<AuthRequest> authValidator;

    private final ObjectsValidator<UserUpdate> updateValidator;

    private final ApplicationUtil applicationUtil;

    @Override
    public UserEntity requestToEntity(User user) {
        validator.validate(user);
        UserEntity userEntity = mapper.map(user, UserEntity.class);
        userEntity.setGender(Gender.UNKNOWN);
        return userEntity;
    }

    @Override
    public UserResponse entityToResponse(UserEntity userEntity) {
        UserResponse userResponse = mapper.map(userEntity, UserResponse.class);
        userResponse.setRole(userEntity.getRole().getName());
        userResponse.setGender(userEntity.getGender().getValue());
        return userResponse;
    }

    public void validateAuth(AuthRequest authRequest) {
        authValidator.validate(authRequest);
    }

    public void validateUpdate(UserUpdate userUpdate) {
        updateValidator.validate(userUpdate);
    }

    public Gender getGenderInRequest(String gender) {
        switch (applicationUtil.toSlug(gender)) {
            case "nam" -> {return Gender.MALE;}
            case "nu" -> {return Gender.FEMALE;}
            case "khong-xac-dinh" -> {return Gender.UNKNOWN;}
            default -> throw new AllException("Data invalid", 404, List.of("Gender invalid"));
        }
    }
}
