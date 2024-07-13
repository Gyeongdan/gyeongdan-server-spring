package gyeongdan.chatBot.term.service;

import gyeongdan.chatBot.term.model.EconomicTerm;
import gyeongdan.chatBot.term.repository.EconomicTermRepository;
import gyeongdan.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EconomicTermService {

    private final EconomicTermRepository economicTermRepository;
    private final MorphologicalAnalysisService wordAnalysisService;
    
    public CommonResponse<Map<String, String>> findEconomicTerm(String question) throws Exception {
        Map<String, Integer> korKeywords = wordAnalysisService.extractKorWords(question);
        List<EconomicTerm> terms = economicTermRepository.findAll();

        // 1. 한국어 형태소 분석기를 통해 추출한 단어와 경제 용어 객체의 term 필드를 비교하여 일치하는지 확인
        CommonResponse<Map<String, String>> response = findTermByKeywords(korKeywords, terms);
        if (response != null) {
            return response;
        }

        // 2. 질문 그 자체가 단어일 때 경제 용어 객체의 term 필드와 질문을 비교하여 일치하는지 확인
        response = findTermByQuestion(question, terms);
        if (response != null) {
            return response;
        }

        // 3. 영어 단어 분석기를 통해 추출한 단어와 경제 용어 객체의 term 필드를 비교하여 일치하는지 확인
        // 추가 로직이 필요할 경우 여기에 작성

        // 4. 실패문
        return new CommonResponse<>(null, "경단어 찾기 실패", false);
    }

    // 1.
    private CommonResponse<Map<String, String>> findTermByKeywords(Map<String, Integer> keywords, List<EconomicTerm> terms) {
        for (EconomicTerm term : terms) {
            for (String keyword : keywords.keySet()) {
                if (term.getTerm().equalsIgnoreCase(keyword)) {
                    return createResponse(term, "경단어 찾기 성공");
                }
            }
        }
        return null;
    }

    // 2.
    private CommonResponse<Map<String, String>> findTermByQuestion(String question, List<EconomicTerm> terms) {
        for (EconomicTerm term : terms) {
            if (term.getTerm().equalsIgnoreCase(question)) {
                return createResponse(term, "경단어 찾기 성공");
            }
        }
        return null;
    }

    private CommonResponse<Map<String, String>> createResponse(EconomicTerm term, String message) {
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("term", term.getTerm());
        responseMap.put("description", term.getDescription());
        responseMap.put("example", term.getExample());
        return new CommonResponse<>(responseMap, message, true);
    }
}
