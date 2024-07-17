package gyeongdan.article.repository;

import gyeongdan.article.domain.ArticleViewHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleViewHistoryJpaRepository extends JpaRepository<ArticleViewHistory, Long> {
    List<ArticleViewHistory> findTop100ByUserIdOrderByViewedAtDesc(Long userId);
}
