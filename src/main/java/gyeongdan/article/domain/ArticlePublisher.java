package gyeongdan.article.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ArticlePublisher {
    MAE_KYUNG("매일 경제"),
    HAN_KYUNG("한국 경제"),
    ;

    private final String name;

}
