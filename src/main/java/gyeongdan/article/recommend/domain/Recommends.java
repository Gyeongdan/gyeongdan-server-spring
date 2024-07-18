package gyeongdan.article.recommend.domain;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;

import java.io.IOException;
import java.util.List;

@Entity
@Table(name = "recommends", schema = "gyeongdan")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recommends {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classificationId;

    @Column(name = "recommend_article_ids", columnDefinition = "jsonb")
    private String recommendArticleIdsJson;

    @Transient
    private List<Long> recommendArticleIds;

    // 활용 메서드들
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @PostLoad
    private void onLoad() {
        this.recommendArticleIds = convertJsonToList(this.recommendArticleIdsJson);
    }

    @PrePersist
    @PreUpdate
    private void onPersist() {
        this.recommendArticleIdsJson = convertListToJson(this.recommendArticleIds);
    }

    private List<Long> convertJsonToList(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<Long>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert JSON to List<Long>", e);
        }
    }

    private String convertListToJson(List<Long> list) {
        try {
            return objectMapper.writeValueAsString(list);
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert List<Long> to JSON", e);
        }
    }
}
