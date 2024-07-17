package gyeongdan.article.repository;

import gyeongdan.article.domain.Article;
import gyeongdan.article.domain.ArticleViewHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticleViewHistoryJpaRepository extends JpaRepository<ArticleViewHistory, Long> {
    List<ArticleViewHistory> findTop100ByOrderByViewedAtDesc();

    @Query("SELECT a.article FROM ArticleViewHistory a WHERE a.viewedAt >= :startOfDay AND a.viewedAt < :endOfDay GROUP BY a.article ORDER BY SUM(a.article.viewCount) DESC")
    List<Article> findTopArticlesByViewedAtBetween(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);
}
