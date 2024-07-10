package gyeongdan.user.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class KakaoLoginResponseDTO {

    private String access_token;
    private String token_type;
    private String refresh_token;
    private long expires_in;
    private String scope;
}
