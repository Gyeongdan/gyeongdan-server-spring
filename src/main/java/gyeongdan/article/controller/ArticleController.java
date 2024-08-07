package gyeongdan.article.controller;

import gyeongdan.article.domain.Article;
import gyeongdan.article.dto.ArticleAllResponse;
import gyeongdan.article.dto.ArticleDetailResponse;
import gyeongdan.article.dto.PopularArticleResponse;
import gyeongdan.article.service.ArticleService;
import gyeongdan.util.CommonResponse;
import gyeongdan.util.JwtUtil;
import gyeongdan.util.annotation.LoginAuthenticated;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
@Slf4j
public class ArticleController {

    private final ArticleService articleService;
    private final JwtUtil jwtUtil;

    // 게시글 상세 조회
    // 단, 유저가 보면 조회기록 저장하고, 유저가 아닌 경우 조회수만 증가시키기
    @GetMapping("/detail")
    public ResponseEntity<?> getArticle(@RequestParam Long id,
                                        @RequestHeader(name = "Authorization") @Nullable String accessToken) { // id : 기사id, access token : 유저의 접근 권한
        Optional<Long> userId = Optional.empty();
        if (accessToken != null && !accessToken.isEmpty()) {
            userId = jwtUtil.getUserId(jwtUtil.resolveToken(accessToken));
        }

        // 기사 조회
        Article article = articleService.getValidArticleById(id, userId);
        // 조회수 증가
        articleService.incrementViewCount(article);

        // 커스터마이징하여 반환
        ArticleDetailResponse articleDetailResponse = new ArticleDetailResponse(article);
        return ResponseEntity.ok(new CommonResponse<>(articleDetailResponse, "게시글 조회 성공", true));
    }

    // 게시물 모두 조회
    @GetMapping("")
    public ResponseEntity<?> getArticles() {
        List<ArticleAllResponse> articles = articleService.getValidArticles();
        return ResponseEntity.ok(new CommonResponse<>(articles, "게시글 모두 조회 성공", true));
    }

    // 최근 조회한 기사 3개 가져오기
    @LoginAuthenticated
    @GetMapping("/recent")
    public ResponseEntity<?> getRecentViewedArticles() {
        // 현재 로그인한 사용자의 ID를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf(authentication.getName());

        // 최근 조회한 기사 3개 가져오기
        List<Article> recentViewedArticles = articleService.getRecentViewedArticles(userId);

        List<ArticleAllResponse> finalResponse = recentViewedArticles.stream()
                .map(article -> new ArticleAllResponse(
                        article.getId(),
                        article.getSimpleTitle(),
                        article.getSimpleContent(),
                        article.getViewCount(),
                        article.getCategory(),
                        Optional.ofNullable(article.getImageUrl()),
                        article.getPublishedAt()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(new CommonResponse<>(finalResponse, "가장 최근에 조회한 게시글 3개 조회 성공", true));
    }

    // 금주 가장 인기 있는 기사 10개 조회 (조회수 기준)
    @GetMapping("/popular")
    public ResponseEntity<?> getPopularArticles() {
        List<PopularArticleResponse> popularArticles = articleService.getPopularArticles();

        return ResponseEntity.ok(new CommonResponse<>(popularArticles, "오늘 가장 인기 있는 게시글 5개 조회 성공", true));
    }
}
