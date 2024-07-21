package gyeongdan.article.controller;

import gyeongdan.article.dto.ArticleAllResponse;
import gyeongdan.article.service.RecommendService;
import gyeongdan.util.CommonResponse;
import gyeongdan.util.annotation.LoginAuthenticated;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class RecommendController {
    private final RecommendService recommendService;

    @LoginAuthenticated
    @GetMapping("/recommend")
    public ResponseEntity<?> getUserTypeArticles() {
        // 현재 로그인한 사용자의 ID를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf(authentication.getName());

        // 추천 알고리즘 결과를 List에 저장 후 반환
        List<ArticleAllResponse> articles = recommendService.recommendArticleById(userId);
        return ResponseEntity.ok(new CommonResponse<>(articles, "추천 기사 10개 가져오기 성공", true));
    }
}
