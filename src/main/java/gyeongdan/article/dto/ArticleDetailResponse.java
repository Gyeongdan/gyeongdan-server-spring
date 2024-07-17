package gyeongdan.article.dto;

import gyeongdan.article.domain.Article;
import gyeongdan.article.domain.ArticleRelatedDocuments;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ArticleDetailResponse {

    private Article article;
    private List<ArticleRelatedDocuments> relatedDocuments;

    public ArticleDetailResponse(Article article, List<ArticleRelatedDocuments> relatedDocuments) {
        this.article = article;
        this.relatedDocuments = relatedDocuments;
    }

}
