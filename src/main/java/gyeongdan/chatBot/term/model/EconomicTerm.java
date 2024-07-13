package gyeongdan.chatBot.term.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity(name = "economic_terms")
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class EconomicTerm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 경제 용어 이름
    @Column(unique = true)
    private String term;
    // 쉬운 경제 용어 설명
    private String description;
    // 경제 용어 사용 예시 문장
    private String example;
}
