package gyeongdan.article.controller;

import gyeongdan.article.domain.Article;
import gyeongdan.article.domain.ArticleViewHistory;
import gyeongdan.article.service.ArticleService;
import gyeongdan.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/detail")
    public ResponseEntity<?> getArticle(@RequestParam Long id) {
        Article article = articleService.getValidArticleById(id);
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
