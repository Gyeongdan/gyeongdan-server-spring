package gyeongdan.util;

import gyeongdan.user.service.KakaoOauthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class LoginAspect {

    @Autowired
    private HttpServletRequest request;
    private final KakaoOauthService kakaoService;

    @Before("@annotation(gyeongdan.util.LoginAuthenticated)")
    public void authenticate() {
        String token = request.getHeader("Authorization");
        if (token == null) {
            throw new RuntimeException("Access Token is missing");
        }

        kakaoService.validateToken(token);
    }
}
