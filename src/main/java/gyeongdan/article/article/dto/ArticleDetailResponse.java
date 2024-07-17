package gyeongdan.article.article.dto;

import gyeongdan.article.article.domain.Article;
import gyeongdan.article.related_documents.domain.ArticleRelatedDocuments;
import java.util.Optional;
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
    private Optional<String> imageUrl;
    private Optional<String> publishedAt;

    public ArticleDetailResponse(Article article) {
        this.id = article.getId();
        this.title = article.getSimpleTitle();
        this.content = article.getSimpleContent();
        this.viewCount = article.getViewCount();
        this.relatedDocuments = article.getRelatedDocuments();
        this.isValid = article.isValid();
        this.imageUrl = Optional.ofNullable(article.getImageUrl());
        this.publishedAt = Optional.ofNullable(article.getPublishedAt()).map(Object::toString);
    }
}
