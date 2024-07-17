package gyeongdan.article.related_documents.repository;

import gyeongdan.article.related_documents.domain.ArticleRelatedDocuments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRelatedDocumentsRepository extends JpaRepository<ArticleRelatedDocuments, Long> {
    List<ArticleRelatedDocuments> findByArticleId(Long articleId);
}
