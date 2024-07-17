package gyeongdan.user.service;

import gyeongdan.user.domain.Users;
import gyeongdan.user.repository.UserManageRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserManageService {

    private final UserManageRepository userManageRepository;

    public void checkUserExist(Long userId) {
        userManageRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("해당 ID의 사용자가 존재하지 않습니다."));
    }

    @Transactional
    public Users addUser(String name, Long kakaoUserId, String profileImage) {
        Optional<Users> existingUserOptional = userManageRepository.findByKakaoUserId(kakaoUserId);

        if (existingUserOptional.isPresent()) {
            Users existingUser = existingUserOptional.get();

            boolean isUpdated = false;

            if (name != null && !name.equals(existingUser.getName())) {
                existingUser.setName(name);
                isUpdated = true;
            }

            if (profileImage != null && !profileImage.equals(existingUser.getProfileImage())) {
                existingUser.setProfileImage(profileImage);
                isUpdated = true;
            }

            if (isUpdated) {
                userManageRepository.save(existingUser);
            }

            return existingUser;
        } else {
            Users newUser = Users.builder()
                .name(name)
                .kakaoUserId(kakaoUserId)
                .profileImage(profileImage)
                .build();

            return userManageRepository.save(newUser);
        }
    }


}
