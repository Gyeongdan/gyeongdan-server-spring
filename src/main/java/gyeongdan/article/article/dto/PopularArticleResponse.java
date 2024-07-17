package gyeongdan.article.article.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PopularArticleResponse {
    private Long id;
    private String title;
    private Long viewCount;
}
