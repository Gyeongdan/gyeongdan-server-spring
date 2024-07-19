package gyeongdan.article.repository;

import gyeongdan.article.domain.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ArticleJpaRepository extends JpaRepository<Article, Long> {

    List<Article> findTop10ByPublishedAtBetweenOrderByViewCountDesc(LocalDateTime startOfWeek, LocalDateTime endOfDay);
}
