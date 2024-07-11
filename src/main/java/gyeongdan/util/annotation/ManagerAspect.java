package gyeongdan.util.annotation;

import gyeongdan.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ManagerAspect {

    @Autowired
    private HttpServletRequest request;
    private final JwtUtil jwtUtil;

    @Before("@annotation(roleAuthenticated)")
    public void authorize(ManagerAuthenticated roleAuthenticated) {
        String token = jwtUtil.resolveToken(request);
        if (token == null || !jwtUtil.validateToken(token)) {
            throw new RuntimeException("Access Token is missing");
        }

        String role = jwtUtil.getRole(token);
        if (!role.equals(roleAuthenticated.role())) {
            throw new RuntimeException("매니저 권한이 필요합니다.");
        }
    }
}
