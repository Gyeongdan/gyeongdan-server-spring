package gyeongdan.user.controller;

import gyeongdan.user.dto.UserTypeTestResult;
import gyeongdan.user.service.UserManageService;
import gyeongdan.util.CommonResponse;
import gyeongdan.util.annotation.LoginAuthenticated;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usertype")
@RequiredArgsConstructor
public class UserTypeController {

    private final UserManageService userManageService;


    @LoginAuthenticated
    @PostMapping("/save")
    public ResponseEntity<?> saveUserTypeTestResult(
        @RequestBody UserTypeTestResult userTypeTestResult
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf(authentication.getName());

        userManageService.saveUserType(userId, userTypeTestResult);

        return ResponseEntity.ok(new CommonResponse<>(null, "유저 타입 테스트 결과 저장 성공", true));
    }
}
