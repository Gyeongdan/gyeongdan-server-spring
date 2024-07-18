package gyeongdan.user.controller;

import gyeongdan.user.service.KakaoOauthService;
import gyeongdan.util.CommonResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class KakaoOauthController {

    private final KakaoOauthService kakaoOauthService;

    @GetMapping("/login")
    public ResponseEntity<?> loginForm() {
        return ResponseEntity
            .ok()
            .body(new CommonResponse<>(
                kakaoOauthService.getKakaoLoginUrl(), "카카오 로그인 URL 생성 성공", true
            ));
    }

    @GetMapping("/logout") // TODO: 매니저 방식으로 로그아웃 처리
    public ResponseEntity<?> logout(@RequestParam String accessToken) {
        kakaoOauthService.getKakaoLogout(accessToken);
        return ResponseEntity
            .ok()
            .body(new CommonResponse<>(null, "로그아웃 성공", true));
    }


    @GetMapping("/login/oauth2/code/kakao")
    public ResponseEntity<?> kakaoOauthCallback(@RequestParam String code) {

        return ResponseEntity
            .ok()
            .body(new CommonResponse<>(
                kakaoOauthService.processKakaoLoginAndGenerateAccessToken(code), "카카오 로그인 성공", true
            ));
    }

    @PostMapping("/login/ok")
    public ResponseEntity<?> loginOk(@RequestParam String code) {
        return ResponseEntity
            .ok()
            .body(new CommonResponse<>(
                kakaoOauthService.processKakaoLoginAndGenerateAccessToken(code), "카카오 로그인 성공", true
            ));
    }
}
