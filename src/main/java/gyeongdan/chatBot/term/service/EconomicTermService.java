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

    private static final Logger logger = LoggerFactory.getLogger(EconomicTermService.class);

    private final EconomicTermRepository economicTermRepository;
    private final MorphologicalAnalysisService wordAnalysisService;
    private final EnglishWordAnalysisService englishWordAnalysisService;

    public Map<String, String> findEconomicTerm(String question) throws Exception {
        Map<String, Integer> korKeywords = wordAnalysisService.extractKorWords(question); // 한국어 키워드 추출
        logger.info("Extracted Korean keywords: {}", korKeywords);

        List<String> combinedNouns = wordAnalysisService.extractAndCombineNouns(question); // 결합된 명사 추출
        logger.info("Combined Nouns: {}", combinedNouns);

        Map<String, Integer> engKeywords = englishWordAnalysisService.extractEngWords(question); // 영어 키워드 추출
        logger.info("Extracted English keywords: {}", engKeywords);

        List<EconomicTerm> terms = economicTermRepository.findAll(); // 경제 용어 목록 불러오기
        logger.info("Loaded economic terms: {}", terms);

        // 1. 한국어 키워드로 검색
        Map<String, String> response = findTermByKeywords(korKeywords, terms);
        if (response != null) {
            return response;
        }

        // 2. 질문 자체로 검색
        response = findTermByQuestion(question, terms);
        if (response != null) {
            return response;
        }

        // 3. 결합된 명사로 검색
        response = findTermByCombinedNouns(combinedNouns, terms);
        if (response != null) {
            return response;
        }

        // 4. 영어 키워드로 검색
        response = findTermByKeywords(engKeywords, terms);
        if (response != null) {
            return response;
        }

        // 5. 실패 시 반환
        logger.info("No matching economic term found for question: {}", question);
        return null;
    }

    private Map<String, String> findTermByKeywords(Map<String, Integer> keywords, List<EconomicTerm> terms) {
        for (EconomicTerm term : terms) {
            for (String keyword : keywords.keySet()) {
                logger.info("Comparing keyword: {} with term: {}", keyword, term.getTerm());
                if (term.getTerm().equalsIgnoreCase(keyword)) {
                    return createResponse(term);
                }
            }
        }
        return null;
    }

    private Map<String, String> findTermByQuestion(String question, List<EconomicTerm> terms) {
        for (EconomicTerm term : terms) {
            if (term.getTerm().equalsIgnoreCase(question)) {
                return createResponse(term);
            }
        }
        return null;
    }

    private Map<String, String> findTermByCombinedNouns(List<String> combinedNouns, List<EconomicTerm> terms) {
        for (EconomicTerm term : terms) {
            for (String combinedNoun : combinedNouns) {
                if (term.getTerm().equalsIgnoreCase(combinedNoun)) {
                    return createResponse(term);
                }
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
