package gyeongdan.article.article.dto;

import gyeongdan.article.article.domain.Article;
import gyeongdan.article.related_documents.domain.ArticleRelatedDocuments;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ArticleDetailResponse {

    private Long id;
    private String title;
    private String content;
    private Long viewCount;
    private List<ArticleRelatedDocuments> relatedDocuments;
    private Boolean isValid;

    public ArticleDetailResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.viewCount = article.getViewCount();
        this.relatedDocuments = article.getRelatedDocuments();
        this.isValid = article.isValid();
    }
}
