package gyeongdan.util.annotation;

import gyeongdan.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class LoginAspect {

    private final HttpServletRequest request;
    private final JwtUtil jwtUtil;

    @Before("@annotation(gyeongdan.util.annotation.LoginAuthenticated)")
    public void authenticate() {
        String token = jwtUtil.resolveToken(request);
        if (token == null || !jwtUtil.validateToken(token)) {
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }

        Long userId = jwtUtil.getUserId(token).orElseThrow(() -> new RuntimeException("사용자 ID를 찾을 수 없습니다."));
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            username, null, Collections.singletonList(new SimpleGrantedAuthority(role))
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}

