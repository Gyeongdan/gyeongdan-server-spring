package gyeongdan.article.controller;

import gyeongdan.article.domain.Article;
import gyeongdan.article.dto.ArticleUpdateRequest;
import gyeongdan.article.service.ArticleService;
import gyeongdan.util.CommonResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/detail")
    public ResponseEntity<?> getArticle(@RequestParam Long id) {
        Article article = articleService.getValidArticle(id);
        return ResponseEntity.ok(new CommonResponse<>(article, "게시글 조회 성공", true));
    }

    @GetMapping("")
    public ResponseEntity<?> getArticles() {
        List<Article> article = articleService.getArticles();
        return ResponseEntity.ok(new CommonResponse<>(article, "게시글 조회 성공", true));
    }

    // TODO: 매니저 인가 필요
    @PostMapping("/validate")
    public ResponseEntity<?> saveArticle(@RequestParam Long id) {
        Article savedArticle = articleService.validateArticle(id);
        return ResponseEntity.ok(new CommonResponse<>(savedArticle, "게시글 저장 성공", true));
    }


    @PostMapping("/update")
    public ResponseEntity<?> updateArticle(@RequestBody ArticleUpdateRequest articleUpdateRequest) {
        Long savedArticleId = articleService.updateArticle(articleUpdateRequest);
        return ResponseEntity.ok(new CommonResponse<>(savedArticleId, "게시글 수정 성공", true));
    }
}

