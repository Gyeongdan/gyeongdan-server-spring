package gyeongdan.user.controller;

import gyeongdan.user.service.KakaoOauthService;
import gyeongdan.util.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KakaoOauthController {

    private final KakaoOauthService kakaoOauthService;

    public KakaoOauthController(KakaoOauthService kakaoOauthService) {
        this.kakaoOauthService = kakaoOauthService;
    }

    @GetMapping("/kakao/oauth")
    public String kakaoOauth() {
        return kakaoOauthService.getKakaoLoginUrl();
    }

    @GetMapping("/kakao-login")
    public ResponseEntity<?> kakaoOauthCallback(@RequestParam String code) {
        return ResponseEntity
            .ok()
            .body(new CommonResponse<>(
                kakaoOauthService.getKakaoAccessToken(code), "카카오 로그인 성공", true
            ));
    }

    @GetMapping("/get-user-info")
    public ResponseEntity<?> getUserInfo(@RequestParam String accessToken) {
        return ResponseEntity
            .ok()
            .body(new CommonResponse<>(
                kakaoOauthService.getUserInfo(accessToken), "유저 정보 조회 성공", true
            ));
    }

}
