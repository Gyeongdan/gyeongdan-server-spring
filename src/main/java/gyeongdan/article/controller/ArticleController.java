package gyeongdan.article.controller;

import gyeongdan.article.domain.Article;
import gyeongdan.article.service.ArticleService;
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
public class ArticleController {

    private final ArticleService articleService;
    private final JwtUtil jwtUtil;

    // 게시글 상세 조회
    // 단, 유저가 보면 조회기록 저장하고, 유저가 아닌 경우 조회수만 증가시키기.
    @GetMapping("/detail")
    public ResponseEntity<?> getArticle(@RequestParam Long id, @RequestHeader @Nullable String accessToken) { // id : 기사id, access token : 유저의 접근 권한
        Optional<Long> userId = Optional.empty();
        if (accessToken != null && !accessToken.isEmpty()) {
            userId = jwtUtil.getUserId(jwtUtil.resolveToken(accessToken));
        }
        Article article = articleService.getValidArticleById(id, userId);
        articleService.incrementViewCount(article);

        return ResponseEntity.ok(new CommonResponse<>(article, "게시글 조회 성공", true));
    }

    @GetMapping("")
    public ResponseEntity<?> getArticles() {
        List<Article> articles = articleService.getValidArticles();
        return ResponseEntity.ok(new CommonResponse<>(articles, "게시글 조회 성공", true));
    }

    // 최근 조회한 기사 3개 가져오기
    @GetMapping("/recent")
    public ResponseEntity<?> getRecentViewedArticles() {
        List<Article> recentViewedArticles = articleService.getRecentViewedArticles();
        return ResponseEntity.ok(new CommonResponse<>(recentViewedArticles, "가장 최근에 조회한 게시글 3개 조회 성공", true));
    }
}
