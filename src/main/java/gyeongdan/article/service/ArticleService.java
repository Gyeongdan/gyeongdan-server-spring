package gyeongdan.article.service;

import gyeongdan.article.domain.Article;
import gyeongdan.article.domain.ArticleViewHistory;
import gyeongdan.article.dto.ArticleUpdateRequest;
import gyeongdan.article.dto.PopularArticleResponse;
import gyeongdan.article.repository.ArticleJpaRepository;
import gyeongdan.article.repository.ArticleRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import gyeongdan.article.repository.ArticleViewHistoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleViewHistoryJpaRepository articleViewHistoryJpaRepository;
    private final ArticleJpaRepository articleJpaRepository;

    public Article getValidArticleById(Long id, Optional<Long> userId) {
        Article article = articleRepository.findById(id);
        checkArticleVisible(article); // id에 해당하는 게시글이 있는지 확인. 없으면 예외 발생

        userId.ifPresent(aLong -> saveViewHistory(aLong, article)); // 만약 userId가 존재하면 조회 기록 저장

        return article;
    }


    private static void checkArticleVisible(Article article) {
        if (Boolean.FALSE.equals(article.getIsValid())) {
            throw new IllegalArgumentException("접근 권한이 없는 게시글입니다.");
        }
    }

    public Article getArticleById(Long id) { // 관리자
        return articleRepository.findById(id);
    }

    public List<Article> getArticles() {
        return articleRepository.findAll();
    }


    public Article validateArticleById(Long id) {
        Article article = articleRepository.findById(id);
        article.setIsValid(true);
        return articleRepository.save(article);
    }

    public Long updateArticle(ArticleUpdateRequest articleUpdateRequest) {
        Article article = articleRepository.findById(articleUpdateRequest.getId());
        article.setTitle(articleUpdateRequest.getTitle());
        article.setContent(articleUpdateRequest.getContent());
        return articleRepository.save(article).getId();
    }


    public List<Article> getValidArticles() {
        return articleRepository.findAll().stream()
                .filter(article -> article != null && article.isValid())
                .toList();
    }

    // 조회수 증가 메서드
    public void incrementViewCount(Article article) {
        article.setViewCount(article.getViewCount() + 1);
        articleRepository.save(article);
    }

    // 조회 기록 저장 메서드
    private void saveViewHistory(Long userId, Article article) {
        articleViewHistoryJpaRepository.save(new ArticleViewHistory(article, userId));
    }

    // 최근 조회한 기사 3개 가져오는 메서드
    public List<Article> getRecentViewedArticles() {
        List<ArticleViewHistory> recentViewedHistories = articleViewHistoryJpaRepository.findTop100ByOrderByViewedAtDesc();
        return recentViewedHistories.stream()
                .map(ArticleViewHistory::getArticle)
                .distinct()
                .limit(3)
                .collect(Collectors.toList());
    }

    // 오늘 가장 인기 있는 기사 5개 가져오는 메서드 (조회수 기준)
    public List<PopularArticleResponse> getPopularArticles() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay();

        List<Article> articles = articleViewHistoryJpaRepository.findTopArticlesByViewedAtBetween(startOfDay, endOfDay);

        return articles.stream()
                .map(article -> new PopularArticleResponse(article.getId(), article.getTitle(), article.getViewCount()))
                .collect(Collectors.toList());
    }
}
