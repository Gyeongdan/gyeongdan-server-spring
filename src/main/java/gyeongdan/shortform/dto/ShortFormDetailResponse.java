package gyeongdan.shortform.dto;

import gyeongdan.article.article.domain.Article;
import gyeongdan.article.related_documents.domain.ArticleRelatedDocuments;
import gyeongdan.shortform.domain.ShortForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
public class ShortFormDetailResponse {
    Long id;
    String title;
    String content;
    Timestamp createdAt;

    public ShortFormDetailResponse(ShortForm shortForm) {
        this.id = shortForm.getId();
        this.title = shortForm.getTitle();
        this.content = shortForm.getContent();
        this.createdAt = shortForm.getCreatedAt();
    }
}
