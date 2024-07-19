package gyeongdan.article.dto;

import gyeongdan.article.domain.Article;
import gyeongdan.article.domain.ArticleRelatedDocuments;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ArticleDetailResponse {

    private Long id;
    private String comment;
    private String title;
    private String publisher;
    private String content;
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
        this.comment = article.getComment();
        this.title = article.getSimpleTitle();
        this.publisher = article.getPublisher();
        this.content = article.getSimpleContent();
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
