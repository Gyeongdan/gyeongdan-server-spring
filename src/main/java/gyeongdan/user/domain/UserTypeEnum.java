package gyeongdan.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserTypeEnum {
    ISSUE_FINDER("이슈파인더"),
    LIFESTYLE_CONSUMER("라이프스타일소비자"),
    ENTERTAINER("엔터테이너"),
    TECH_SPECIALIST("테크전문가"),
    PROFESSIONALS("전문가");

    private final String name;

    public static UserTypeEnum from(String name) {
        for (UserTypeEnum userType : UserTypeEnum.values()) {
            if (userType.name.equals(name)) {
                return userType;
            }
        }
        throw new IllegalArgumentException("Invalid name: " + name);
    }

    public static UserTypeEnum fromWeights(Long issueFinder, Long lifestyleConsumer, Long entertainer,
        Long techSpecialist, Long professionals) {
        long maxWeight = issueFinder;
        UserTypeEnum strongestUserType = ISSUE_FINDER;

        if (lifestyleConsumer > maxWeight) {
            maxWeight = lifestyleConsumer;
            strongestUserType = LIFESTYLE_CONSUMER;
        }
        if (entertainer > maxWeight) {
            maxWeight = entertainer;
            strongestUserType = ENTERTAINER;
        }
        if (techSpecialist > maxWeight) {
            maxWeight = techSpecialist;
            strongestUserType = TECH_SPECIALIST;
        }
        if (professionals > maxWeight) {
            maxWeight = professionals;
            strongestUserType = PROFESSIONALS;
        }

        return strongestUserType;
    }
}
