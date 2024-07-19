package gyeongdan.article.repository;

import gyeongdan.article.domain.Article;
import gyeongdan.article.domain.ArticleViewHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticleViewHistoryJpaRepository extends JpaRepository<ArticleViewHistory, Long> {
    List<ArticleViewHistory> findTop100ByUserIdOrderByViewedAtDesc(Long userId);
}
