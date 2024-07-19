package gyeongdan.article.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ArticlePublisher {
    MAE_KYUNG("매일경제"),
    HAN_KYUNG("한국경제"),
    ;

    private final String name;

}
