package gyeongdan.article.type_test.controller;

import gyeongdan.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class TypeTestController {
    // 유형 검사 결과 랜덤하게 반환하기
    @GetMapping("/type-test")
    public ResponseEntity<?> getTypeTest() {
        String[] userTypes = {
                "IssueFinder",
                "LifestyleConsumer",
                "Entertainer",
                "TechSpecialist",
                "Professionals"
        };

        Random random = new Random();
        int randomIndex = random.nextInt(userTypes.length);
        String randomUserType = userTypes[randomIndex];

        return ResponseEntity.ok(new CommonResponse<>(randomUserType, "유형 검사 결과 랜덤하게 반환하기", true));
    }
}
