package gyeongdan.article.article.dto;

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
    private Timestamp createdAt;
}
