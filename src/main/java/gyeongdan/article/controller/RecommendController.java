package gyeongdan.article.controller;

import gyeongdan.article.dto.ArticleAllResponse;
import gyeongdan.article.service.RecommendService;
import gyeongdan.util.CommonResponse;
import gyeongdan.util.JwtUtil;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class RecommendController {
    private final JwtUtil jwtUtil;
    private final RecommendService recommendService;

    @GetMapping("/recommend")
    public ResponseEntity<?> getUserTypeArticles(@RequestHeader @NotNull String accessToken) { // id : 기사id, access token : 유저의 접근 권한
        if (!jwtUtil.validateToken(accessToken)) {
            throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
        }

        Optional<Long> userId = jwtUtil.getUserId(jwtUtil.resolveToken(accessToken));

        // 추천 알고리즘 결과를 List에 저장 후 반환
        List<ArticleAllResponse> articles = recommendService.recommendArticleById(userId);
        return ResponseEntity.ok(new CommonResponse<>(articles, "추천 기사 10개 가져오기 성공", true));
    }
}
