package gyeongdan.user.dto;

import gyeongdan.user.domain.UserTypeEnum;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UserTypeTestResult {

    private Long userTypeIssueFinder;
    private Long userTypeLifestyleConsumer;
    private Long userTypeEntertainer;
    private Long userTypeTechSpecialist;
    private Long userTypeProfessionals;
    private UserTypeEnum userType;
}
