package gyeongdan.user.controller;

import gyeongdan.user.domain.UserProfile;
import gyeongdan.user.service.UserManageService;
import gyeongdan.util.CommonResponse;
import gyeongdan.util.annotation.LoginAuthenticated;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserManageService userManageService;

    @GetMapping("/profile")
    @LoginAuthenticated
    public ResponseEntity<?> getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf(authentication.getName());
        UserProfile userProfile = userManageService.getProfile(userId);

        return ResponseEntity.ok(new CommonResponse<>(
                userProfile, "유저 이름 가져오기 성공", true
        ));
    }

}
