package gyeongdan.chatBot.introduce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity(name = "faq")
@Table(name = "faq", schema = "gyeongdan")
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class FAQ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String question;
    private String answer;

}
