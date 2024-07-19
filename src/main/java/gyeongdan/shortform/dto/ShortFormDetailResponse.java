package gyeongdan.shortform.dto;

import gyeongdan.shortform.domain.ShortForm;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class ShortFormDetailResponse {
    Long id;
    String title;
    String content;
    Timestamp createdAt;

    public ShortFormDetailResponse(ShortForm shortForm) {
        this.id = shortForm.getId();
        this.title = shortForm.getTitle();
        this.content = shortForm.getContent();
        this.createdAt = shortForm.getCreatedAt();
    }
}
