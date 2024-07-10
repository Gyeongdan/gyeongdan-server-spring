package gyeongdan.user.service;

import gyeongdan.user.dto.KakaoLoginResponseDTO;
import gyeongdan.user.dto.KakaoProfile;
import gyeongdan.user.dto.KakaoTokenInfo;
import gyeongdan.user.exception.UserException;
import gyeongdan.util.ErrorCode;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoOauthService {

    @Value("${kakao.client.id}")
    private String clientId;

    @Value("${kakao.redirect.uri}")
    private String redirectUri;

    private final RestClient.Builder restClient;
    private final UserManageService userManageService;

    public String getKakaoLoginUrl() {
        Map<String, String> queryparams = Map.of(
            "client_id", clientId,
            "redirect_uri", redirectUri,
            "response_type", "code"
        );

        String queryString = queryparams.entrySet()
            .stream()
            .map(entry -> entry.getKey() + "=" + entry.getValue())
            .collect(Collectors.joining("&"));

        return "https://kauth.kakao.com/oauth/authorize?" + queryString;
    }

    public KakaoLoginResponseDTO getKakaoAccessToken(String code) {
        Map<String, String> queryParams = Map.of(
            "grant_type", "authorization_code",
            "client_id", clientId,
            "redirect_uri", redirectUri,
            "code", code
        );

        String queryString = queryParams.entrySet()
            .stream()
            .map(entry -> entry.getKey() + "=" + entry.getValue())
            .collect(Collectors.joining("&"));

        String uri = "https://kauth.kakao.com/oauth/token?" + queryString;

        return restClient
            .build()
            .post()
            .uri(uri)
            .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
            .retrieve()
            .body(KakaoLoginResponseDTO.class);
    }

    public KakaoProfile getKakaoUserProfile(String accessToken) {
        String uri = "https://kapi.kakao.com/v2/user/me";

        return restClient
            .build()
            .get()
            .uri(uri)
            .header("Authorization", "Bearer " + accessToken)
            .retrieve()
            .body(KakaoProfile.class);
    }

    public void getKakaoLogout(String accessToken) {
        String uri = "https://kapi.kakao.com/v1/user/logout";

        restClient
            .build()
            .post()
            .uri(uri)
            .header("Authorization", "Bearer " + accessToken)
            .retrieve()
            .body(KakaoProfile.class);
    }

    public KakaoLoginResponseDTO processKakaoLogin(String code) {
        // 1. 카카오에서 액세스 토큰 획득
        KakaoLoginResponseDTO tokenResponse = getKakaoAccessToken(code);
        String accessToken = tokenResponse.getAccessToken();

        // 2. 액세스 토큰을 사용하여 사용자 프로필 가져오기
        KakaoProfile profile = getKakaoUserProfile(accessToken);

        // 3. 사용자 정보 저장
        userManageService.addUser(profile.getProperties().getNickname(), profile.getId());

        return tokenResponse;
    }

    public KakaoTokenInfo validateToken(String accessToken) {
        String uri = "https://kapi.kakao.com/v1/user/access_token_info";
        return restClient
            .build()
            .get()
            .uri(uri)
            .header("Authorization", "Bearer " + accessToken)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new UserException(ErrorCode.LOGIN_TOKEN_INVALID);
                }
            )
            .body(KakaoTokenInfo.class);
    }
}

