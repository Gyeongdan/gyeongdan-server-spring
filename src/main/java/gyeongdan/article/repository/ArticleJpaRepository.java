package gyeongdan.article.repository;

import gyeongdan.article.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ArticleJpaRepository extends JpaRepository<Article, Long> {
    @Query("SELECT a FROM Article a ORDER BY " +
            "CASE WHEN a.publishedAt IS NULL THEN 1 ELSE 0 END, " +
            "a.publishedAt DESC, " +
            "a.createdAt DESC")
    List<Article> findAllOrderByPublishedAtOrCreatedAtDesc();

    List<Article> findTop10ByPublishedAtBetweenAndIsValidTrueOrderByViewCountDesc(LocalDateTime startOfWeek, LocalDateTime endOfDay);
}
