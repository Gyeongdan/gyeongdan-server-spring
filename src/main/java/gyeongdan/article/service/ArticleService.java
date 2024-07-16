package gyeongdan.article.service;

import gyeongdan.article.domain.Article;
import gyeongdan.article.domain.ArticleViewHistory;
import gyeongdan.article.dto.ArticleUpdateRequest;
import gyeongdan.article.repository.ArticleRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import gyeongdan.article.repository.ArticleViewHistoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleViewHistoryJpaRepository articleViewHistoryJpaRepository;

    public Article getValidArticleById(Long id) { // 일반 사용자
        Article article = articleRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 ID의 게시글이 존재하지 않습니다."));
        if (Boolean.TRUE.equals(article.getIsValid())) {
            return article;
        } else {
            throw new IllegalArgumentException("접근 권한이 없는 게시글입니다.");
        }
    }

    public Article getArticleById(Long id) { // 관리자
        return articleRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 ID의 게시글이 존재하지 않습니다."));
    }

    public List<Article> getArticles() {
        return articleRepository.findAll();
    }


    public Article validateArticle(Long id) {
        Article article = articleRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 ID의 게시글이 존재하지 않습니다."));
        article.setIsValid(true);
        return articleRepository.save(article);
    }

    public Long updateArticle(ArticleUpdateRequest articleUpdateRequest) {
        Article article = articleRepository.findById(articleUpdateRequest.getId())
            .orElseThrow(() -> new IllegalArgumentException("해당 ID의 게시글이 존재하지 않습니다."));
        article.setTitle(articleUpdateRequest.getTitle());
        article.setContent(articleUpdateRequest.getContent());
        return articleRepository.save(article).getId();
    }

    public List<Article> getValidArticles() {
        return articleRepository.findAll().stream()
            .filter(article -> article != null && article.isValid())
            .toList();
    }

    // 조회수 증가 메서드 추가
    public void incrementViewCount(Article article) {
        article.setViewCount(article.getViewCount() + 1);
        articleRepository.save(article);

        // 조회 기록 저장 추가
        ArticleViewHistory viewHistory = new ArticleViewHistory(article);

        articleViewHistoryJpaRepository.save(viewHistory);
    }

    // 최근 조회한 기사 3개 가져오기 메서드 추가
    public List<Article> getRecentViewedArticles() {
        List<ArticleViewHistory> recentViewedHistories = articleViewHistoryJpaRepository.findTop100ByOrderByViewedAtDesc();
        return recentViewedHistories.stream()
                .map(ArticleViewHistory::getArticle)
                .distinct()
                .limit(3)
                .collect(Collectors.toList());
    }
}
