package gyeongdan.user.service;

import gyeongdan.user.dto.KakaoLoginResponseDTO;
import gyeongdan.user.dto.KakaoProfile;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.Builder;

@Service
@Slf4j
public class KakaoOauthService {

    @Value("${kakao.client.id}")
    private String clientId;

    @Value("${kakao.redirect.uri}")
    private String redirectUri;

    private final RestClient.Builder restClient;

    public KakaoOauthService(Builder restClient) {
        this.restClient = restClient;
    }

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

        KakaoLoginResponseDTO result = restClient
            .build()
            .post()
            .uri(uri)
            .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
            .retrieve()
            .body(KakaoLoginResponseDTO.class);

        return result;
    }

    public KakaoProfile getUserInfo(String accessToken) {
        String uri = "https://kapi.kakao.com/v2/user/me";

        return restClient
            .build()
            .get()
            .uri(uri)
            .header("Authorization", "Bearer " + accessToken)
            .retrieve()
            .body(KakaoProfile.class);
    }
}


