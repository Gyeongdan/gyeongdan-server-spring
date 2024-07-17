package gyeongdan.article.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "article_view_history", schema = "gyeongdan")
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ArticleViewHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Column(name = "viewed_at", nullable = false)
    private LocalDateTime viewedAt = LocalDateTime.now();

    @Column(name = "user_id")
    private Long userId;

    public ArticleViewHistory(Article article) {
        this.article = article;
    }

    public ArticleViewHistory(Article article, Long userId) {
        this.article = article;
        this.userId = userId;
    }
}
