package sang.se.bookingmovie.app.role;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    @Autowired
    private final RoleRepository roleRepository;
}
