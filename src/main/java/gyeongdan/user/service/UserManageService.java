package gyeongdan.user.service;

import gyeongdan.user.domain.Users;
import gyeongdan.user.repository.UserManageRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserManageService {

    private final UserManageRepository userManageRepository;

    public void addUser(String name, Long kakaoUserId) {
        Optional<Users> existingUser = userManageRepository.findByKakaoUserId(kakaoUserId);

        if (existingUser.isPresent()) {
            existingUser.get();
        } else {
            Users newUser = Users.builder()
                .name(name)
                .kakaoUserId(kakaoUserId)
                .build();

            userManageRepository.save(newUser);
        }
    }
}
