package gyeongdan.article.article.controller;

import gyeongdan.article.article.domain.Article;
import gyeongdan.article.article.dto.ArticleUpdateRequest;
import gyeongdan.article.article.service.ArticleService;
import gyeongdan.util.CommonResponse;
import gyeongdan.util.annotation.AdminAuthenticated;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/article")
@RequiredArgsConstructor
public class AdminArticleController {

    private final ArticleService articleService;

    @GetMapping("")
    @AdminAuthenticated
    public ResponseEntity<?> getArticlesAdmin() {
        List<Article> articles = articleService.getArticles();
        return ResponseEntity.ok(new CommonResponse<>(articles, "게시글 조회 성공", true));
    }

    @PostMapping("/update")
    @AdminAuthenticated
    public ResponseEntity<?> updateArticle(@RequestBody ArticleUpdateRequest articleUpdateRequest) {
        Long savedArticleId = articleService.updateArticle(articleUpdateRequest);
        return ResponseEntity.ok(new CommonResponse<>(savedArticleId, "게시글 수정 성공", true));
    }

    @GetMapping("/detail")
    @AdminAuthenticated
    public ResponseEntity<?> getArticleAdmin(@RequestBody Long id) {
        Article article = articleService.getArticleById(id);
        return ResponseEntity.ok(new CommonResponse<>(article, "게시글 조회 성공", true));
    }

}
