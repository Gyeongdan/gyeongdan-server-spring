package gyeongdan.article.service;

import gyeongdan.article.domain.ArticleRelatedDocuments;
import gyeongdan.article.repository.ArticleRelatedDocumentsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleRelatedDocumentsService {

    private final ArticleRelatedDocumentsRepository articleRelatedDocumentsRepository;

    public ArticleRelatedDocumentsService(ArticleRelatedDocumentsRepository articleRelatedDocumentsRepository) {
        this.articleRelatedDocumentsRepository = articleRelatedDocumentsRepository;
    }

    public List<ArticleRelatedDocuments> getRelatedDocuments(Long articleId) {
        return articleRelatedDocumentsRepository.findByArticleId(articleId);
    }
}
