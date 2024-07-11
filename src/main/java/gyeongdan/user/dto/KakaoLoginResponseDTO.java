package gyeongdan.user.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)

public class KakaoLoginResponseDTO {

    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private long expiresIn;
    private String scope;
}
