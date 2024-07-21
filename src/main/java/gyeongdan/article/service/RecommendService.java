package gyeongdan.article.service;

import gyeongdan.article.dto.ArticleAllResponse;
import gyeongdan.article.repository.ArticleJpaRepository;
import gyeongdan.article.domain.Recommends;
import gyeongdan.user.domain.UserType;
import gyeongdan.article.repository.RecommendJpaRepository;
import gyeongdan.user.repository.UserTypeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendService {
    private final RecommendJpaRepository recommendJpaRepository;
    private final UserTypeJpaRepository userTypeJpaRepository;
    private final ArticleJpaRepository articleJpaRepository;

    public List<ArticleAllResponse> recommendArticleById(Long userId) {
        // (1) 사용자 id에 해당하는 UserType을 가져옴
        UserType userType = userTypeJpaRepository.findTopByUserIdOrderByIdDesc(userId)
                .orElseThrow(() -> new IllegalArgumentException("아직 유형검사를 하지 않은 유저입니다."));

        // (2) UserType에서 가장 값이 높은 3개의 타입값을 추출하기
        Map<String, Long> userTypeValues = new HashMap<>();
        userTypeValues.put("ISSUE_FINDER", userType.getUserTypeIssueFinder());
        userTypeValues.put("LIFESTYLE_CONSUMER", userType.getUserTypeLifestyleConsumer());
        userTypeValues.put("ENTERTAINER", userType.getUserTypeEntertainer());
        userTypeValues.put("TECH_SPECIALIST", userType.getUserTypeTechSpecialist());
        userTypeValues.put("PROFESSIONALS", userType.getUserTypeProfessionals());

        List<String> top3UserTypes = userTypeValues.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // (3) top3UserTypes를 기반으로 classification_id를 결정
        int classificationId = determineClassificationId(top3UserTypes);

        // (4) classification_id에 해당하는 추천 기사 ID들을 가져옴
        Recommends recommends = recommendJpaRepository.findById((long) classificationId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid classification ID"));

        // 추천 기사 ID들에 해당하는 ArticleAllResponse 리스트를 반환
        return fetchArticlesByIds(recommends.getRecommendArticleIds());
    }

    private int determineClassificationId(List<String> top3UserTypes) {
        // 데이터 매핑 (파이썬의 데이터 프레임에 해당)
        Map<Integer, Map<String, Integer>> data = new HashMap<>();
        data.put(1, Map.of("ISSUE_FINDER", 1, "LIFESTYLE_CONSUMER", 1, "ENTERTAINER", 1, "TECH_SPECIALIST", 0, "PROFESSIONALS", 0));
        data.put(2, Map.of("ISSUE_FINDER", 1, "LIFESTYLE_CONSUMER", 1, "ENTERTAINER", 0, "TECH_SPECIALIST", 1, "PROFESSIONALS", 0));
        data.put(3, Map.of("ISSUE_FINDER", 1, "LIFESTYLE_CONSUMER", 1, "ENTERTAINER", 0, "TECH_SPECIALIST", 0, "PROFESSIONALS", 1));
        data.put(4, Map.of("ISSUE_FINDER", 1, "LIFESTYLE_CONSUMER", 0, "ENTERTAINER", 1, "TECH_SPECIALIST", 1, "PROFESSIONALS", 0));
        data.put(5, Map.of("ISSUE_FINDER", 1, "LIFESTYLE_CONSUMER", 0, "ENTERTAINER", 1, "TECH_SPECIALIST", 0, "PROFESSIONALS", 1));
        data.put(6, Map.of("ISSUE_FINDER", 1, "LIFESTYLE_CONSUMER", 0, "ENTERTAINER", 0, "TECH_SPECIALIST", 1, "PROFESSIONALS", 1));
        data.put(7, Map.of("ISSUE_FINDER", 0, "LIFESTYLE_CONSUMER", 1, "ENTERTAINER", 1, "TECH_SPECIALIST", 1, "PROFESSIONALS", 0));
        data.put(8, Map.of("ISSUE_FINDER", 0, "LIFESTYLE_CONSUMER", 1, "ENTERTAINER", 1, "TECH_SPECIALIST", 0, "PROFESSIONALS", 1));
        data.put(9, Map.of("ISSUE_FINDER", 0, "LIFESTYLE_CONSUMER", 1, "ENTERTAINER", 0, "TECH_SPECIALIST", 1, "PROFESSIONALS", 1));
        data.put(10, Map.of("ISSUE_FINDER", 0, "LIFESTYLE_CONSUMER", 0, "ENTERTAINER", 1, "TECH_SPECIALIST", 1, "PROFESSIONALS", 1));

        // (3) top3UserTypes를 기반으로 classification_id를 결정
        for (Map.Entry<Integer, Map<String, Integer>> entry : data.entrySet()) {
            Map<String, Integer> userTypeMap = entry.getValue();
            boolean matches = true;
            for (String userType : top3UserTypes) {
                if (userTypeMap.getOrDefault(userType, 0) != 1) {
                    matches = false;
                    break;
                }
            }
            if (matches) {
                return entry.getKey();
            }
        }

        throw new IllegalArgumentException("User type 3개를 기반으로 classification_id를 결정할 수 없습니다!");
    }

    // (4) ArticleAllResponse 리스트를 반환하는 메서드
    private List<ArticleAllResponse> fetchArticlesByIds(List<Long> articleIds) {
        return articleIds.stream()
                .map(articleJpaRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(article -> new ArticleAllResponse(
                        article.getId(),
                        article.getSimpleTitle(),
                        article.getSimpleContent(),
                        article.getViewCount(),
                        article.getCategory(),
                        Optional.ofNullable(article.getImageUrl()),
                        article.getPublishedAt()
                ))
                .collect(Collectors.toList());
    }
}
