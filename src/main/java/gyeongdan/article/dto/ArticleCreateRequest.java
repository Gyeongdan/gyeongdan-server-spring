package gyeongdan.article.dto;

import gyeongdan.article.domain.Article;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ArticleCreateRequest {

    private String title;
    private String content;

    public Article toEntity() {
        return Article.builder()
            .title(title)
            .content(content)
            .isValid(true)
            .build();
    }
}
