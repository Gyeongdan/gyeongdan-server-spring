package gyeongdan.user.controller;

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

    @GetMapping("/name")
    @LoginAuthenticated
    public ResponseEntity<?> getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf(authentication.getName());

        return ResponseEntity.ok(new CommonResponse<>(
            userManageService.getUser(userId).getName(), "유저 이름 가져오기 성공", true
        ));
    }

}
