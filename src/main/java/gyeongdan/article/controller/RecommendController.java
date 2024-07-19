package gyeongdan.article.controller;

import gyeongdan.article.dto.ArticleAllResponse;
import gyeongdan.article.service.RecommendService;
import gyeongdan.util.CommonResponse;
import gyeongdan.util.JwtUtil;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<?> getUserTypeArticles(@RequestHeader @Nullable String accessToken) { // id : 기사id, access token : 유저의 접근 권한
        Optional<Long> userId = Optional.empty();
        if (accessToken != null && !accessToken.isEmpty()) {
            userId = jwtUtil.getUserId(jwtUtil.resolveToken(accessToken));
        }

        // 추천 알고리즘
        List<ArticleAllResponse> articles = recommendService.recommendArticleById(userId);

        return ResponseEntity.ok(new CommonResponse<>(articles, "추천 기사 10개 가져오기 성공", true));
    }
}
