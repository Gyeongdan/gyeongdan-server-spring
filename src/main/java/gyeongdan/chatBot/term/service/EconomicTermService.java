package gyeongdan.chatBot.term.service;

import gyeongdan.chatBot.term.model.EconomicTerm;
import gyeongdan.chatBot.term.repository.EconomicTermRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EconomicTermService {

    private final EconomicTermRepository economicTermRepository;
    private final MorphologicalAnalysisService wordAnalysisService;
    private final EnglishWordAnalysisService englishWordAnalysisService;
    private static final Logger logger = LoggerFactory.getLogger(EconomicTermService.class);

    public Map<String, String> findEconomicTerm(String question) throws Exception {
        Map<String, Integer> korKeywords = wordAnalysisService.extractKorWords(question);
        List<EconomicTerm> terms = economicTermRepository.findAll();

        // 1. 한국어 형태소 분석기를 통해 추출한 단어와 경제 용어 객체의 term 필드를 비교하여 일치하는지 확인
        Map<String, String> response = findTermByKeywords(korKeywords, terms);
        if (response != null) {
            return response;
        }

        // 2. 질문 그 자체가 단어일 때 경제 용어 객체의 term 필드와 질문을 비교하여 일치하는지 확인
        response = findTermByQuestion(question, terms);
        if (response != null) {
            return response;
        }

        // 3. 영어 단어 분석기를 통해 추출한 단어와 경제 용어 객체의 term 필드를 비교하여 일치하는지 확인
        Map<String, Integer> engKeywords = englishWordAnalysisService.extractEngWords(question);
        logger.debug("Extracted English keywords: {}", engKeywords);
        response = findTermByKeywords(engKeywords, terms);
        if (response != null) {
            return response;
        }

        // 4. 실패문
        logger.debug("No matching economic term found for question: {}", question);
        return null;
    }

    // 1.
    private Map<String, String> findTermByKeywords(Map<String, Integer> keywords, List<EconomicTerm> terms) {
        for (EconomicTerm term : terms) {
            for (String keyword : keywords.keySet()) {
                if (term.getTerm().equalsIgnoreCase(keyword)) {
                    return createResponse(term);
                }
            }
        }
        return null;
    }

    // 2.
    private Map<String, String> findTermByQuestion(String question, List<EconomicTerm> terms) {
        for (EconomicTerm term : terms) {
            if (term.getTerm().equalsIgnoreCase(question)) {
                return createResponse(term);
            }
        }
        return null;
    }

    private Map<String, String> createResponse(EconomicTerm term) {
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("term", term.getTerm());
        responseMap.put("description", term.getDescription());
        responseMap.put("example", term.getExample());
        return responseMap;
    }
}
