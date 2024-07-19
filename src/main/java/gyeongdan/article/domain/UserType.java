package gyeongdan.article.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_type", schema = "gyeongdan")
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private Long userTypeIssueFinder;
    private Long userTypeLifestyleConsumer;
    private Long userTypeEntertainer;
    private Long userTypeTechSpecialist;
    private Long userTypeProfessionals;
    private Long userType;
}
