package gyeongdan.article.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ArticleUpdateRequest {

    private Long id;
    private String title;
    private String content;
}
