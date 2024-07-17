package gyeongdan.article.dto;

import gyeongdan.article.domain.Article;
import gyeongdan.article.domain.ArticleRelatedDocuments;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

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
