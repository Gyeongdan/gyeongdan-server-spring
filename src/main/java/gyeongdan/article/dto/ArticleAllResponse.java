package gyeongdan.article.dto;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class ArticleAllResponse {

    private Long id;
    private String title;
    private String content;
    private Long viewCount;
    private String category;
    private Optional<String> imageUrl;
    private LocalDateTime publishedAt;
}
