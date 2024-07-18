package gyeongdan.shortform.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "api_visualization", schema = "gyeongdan")
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ShortForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private Timestamp createdAt;
}
