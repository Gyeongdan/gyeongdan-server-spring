package gyeongdan.article.article.dto;

import gyeongdan.article.article.domain.Article;
import gyeongdan.article.related_documents.domain.ArticleRelatedDocuments;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ArticleDetailResponse {

    private Long id;
    private String title;
    private String comment;
    private String simpleTitle;
    private String publisher;
    private String simpleContent;
    private LocalDateTime createdAt;
    private LocalDateTime publishedAt;
    private String url;
    private Map<String, Object> phrase;
    private String category;
    private Boolean isValid;
    private Long viewCount;
    private String imageUrl;

    private List<ArticleRelatedDocuments> relatedDocuments;

    public ArticleDetailResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.comment = article.getComment();
        this.simpleTitle = article.getSimpleTitle();
        this.publisher = article.getPublisher();
        this.simpleContent = article.getSimpleContent();
        this.createdAt = article.getCreatedAt();
        this.publishedAt = article.getPublishedAt();
        this.url = article.getUrl();
        this.phrase = article.getPhrase();
        this.category = article.getCategory();
        this.isValid = article.getIsValid();
        this.viewCount = article.getViewCount();
        this.imageUrl = article.getImageUrl();
        this.relatedDocuments = article.getRelatedDocuments();
    }
}
