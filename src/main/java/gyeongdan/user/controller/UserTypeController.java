package gyeongdan.user.controller;

import gyeongdan.util.CommonResponse;
import gyeongdan.util.annotation.LoginAuthenticated;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usertype")
@RequiredArgsConstructor
public class UserTypeController {



    @LoginAuthenticated
    @PostMapping("/save")
    public ResponseEntity<?> saveUserTypeTestResult() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName(); // 사용자 ID 가져오기
//        String role = authentication.getAuthorities().iterator().next().getAuthority();



        return ResponseEntity.ok(new CommonResponse<>(null, "유저 타입 테스트 결과 저장 성공", true));
    }
}
