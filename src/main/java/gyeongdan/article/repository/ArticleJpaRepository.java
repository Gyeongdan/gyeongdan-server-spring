package gyeongdan.article.repository;

import gyeongdan.article.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ArticleJpaRepository extends JpaRepository<Article, Long> {
    // 게시물 전체 조회 : publishedAt이 없는 경우 createdAt으로 정렬. isValid 값이 True인 경우만 조회
    @Query("SELECT a FROM Article a WHERE a.isValid = true ORDER BY " +
            "CASE WHEN a.publishedAt IS NULL THEN 1 ELSE 0 END, " +
            "a.publishedAt DESC, " +
            "a.createdAt DESC")
    List<Article> findAllValidOrderByPublishedAtOrCreatedAtDesc();

    List<Article> findTop10ByPublishedAtBetweenAndIsValidTrueOrderByViewCountDesc(LocalDateTime startOfWeek, LocalDateTime endOfDay);
}
