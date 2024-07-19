package gyeongdan.article.repository;

import gyeongdan.article.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ArticleJpaRepository extends JpaRepository<Article, Long> {

    @Query("SELECT a FROM Article a WHERE a.publishedAt >= :startOfWeek AND a.publishedAt < :endOfWeek ORDER BY a.viewCount DESC")
    List<Article> findTopArticlesByPublishedAtBetween(@Param("startOfWeek") LocalDateTime startOfWeek, @Param("endOfWeek") LocalDateTime endOfWeek);
}
