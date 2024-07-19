package gyeongdan.user.dto;

import gyeongdan.user.domain.UserTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserTypeRecord {

    private Long userTypeIssueFinder;
    private Long userTypeLifestyleConsumer;
    private Long userTypeEntertainer;
    private Long userTypeTechSpecialist;
    private Long userTypeProfessionals;
    private UserTypeEnum userType;
}
