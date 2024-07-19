package gyeongdan.article.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "articles", schema = "gyeongdan")
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String comment;
    private String simpleTitle;
    private String publisher;
    private String simpleContent;
    private LocalDateTime createdAt;
    @Nullable
    private LocalDateTime publishedAt;
    private String url;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> phrase;

    private String category;
    @Nullable
    private Boolean isValid;
    private Long viewCount;
    @Nullable
    private String imageUrl;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ArticleViewHistory> viewHistories = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleRelatedDocuments> relatedDocuments;

    public LocalDateTime getPublishedAt() {
        return publishedAt != null ? publishedAt : createdAt;
    }

    // 활용 메서드들
    public boolean isValid() {
        if (isValid == null) {
            return false;
        }
        return isValid;
    }

    // 조회 기록
    public void addViewHistory(ArticleViewHistory viewHistory) {
        this.viewHistories.add(viewHistory);
        viewHistory.setArticle(this);
    }

    public void removeViewHistory(ArticleViewHistory viewHistory) {
        viewHistories.remove(viewHistory);
        viewHistory.setArticle(null);
    }

    // 관련 문서
    public void addRelatedDocument(ArticleRelatedDocuments relatedDocument) {
        this.relatedDocuments.add(relatedDocument);
        relatedDocument.setArticle(this);
    }

    public void removeRelatedDocument(ArticleRelatedDocuments relatedDocument) {
        relatedDocuments.remove(relatedDocument);
        relatedDocument.setArticle(null);
    }
}
