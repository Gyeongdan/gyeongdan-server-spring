package gyeongdan.user.service;

import gyeongdan.user.domain.UserType;
import gyeongdan.user.domain.Users;
import gyeongdan.user.dto.UserTypeTestResult;
import gyeongdan.user.repository.UserManageRepository;
import gyeongdan.user.repository.UserTypeJpaRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserManageService {

    private final UserManageRepository userManageRepository;
    private final UserTypeJpaRepository userTypeJpaRepository;

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

    @Transactional
    public void saveUserType(Long userId, UserTypeTestResult userTypeTestResult) {
        checkUserExist(userId);
        userTypeJpaRepository.save(
            UserType.builder()
                .userId(userId)
                .userTypeIssueFinder(userTypeTestResult.getUserTypeIssueFinder())
                .userTypeLifestyleConsumer(userTypeTestResult.getUserTypeLifestyleConsumer())
                .userTypeEntertainer(userTypeTestResult.getUserTypeEntertainer())
                .userTypeTechSpecialist(userTypeTestResult.getUserTypeTechSpecialist())
                .userTypeProfessionals(userTypeTestResult.getUserTypeProfessionals())
                .build()
        );
    }

}
