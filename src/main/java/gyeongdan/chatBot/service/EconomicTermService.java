package gyeongdan.chatBot.service;

import gyeongdan.chatBot.model.EconomicTerm;
import gyeongdan.chatBot.repository.EconomicTermRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EconomicTermService {

    private final EconomicTermRepository economicTermRepository;
    private final WordAnalysisService wordAnalysisService;

    public EconomicTermService(EconomicTermRepository economicTermRepository, WordAnalysisService wordAnalysisService) {
        this.economicTermRepository = economicTermRepository;
        this.wordAnalysisService = wordAnalysisService;
    }

    public String findEconomicTerm(String question) throws Exception {
        Map<String, Integer> keywords = extractKeywords(question);
        List<EconomicTerm> terms = economicTermRepository.findAll();

        for (EconomicTerm term : terms) {
            // 경제 용어 객체의 term 필드와 keywords 리스트의 요소들을 비교하여 일치하는지 확인
            for (String keyword : keywords.keySet()) {
                if (term.getTerm().equalsIgnoreCase(keyword)) {
                    return term.getDescription();
                }
            }
        }

        return "질문에서 관련된 경제 용어를 찾을 수 없습니다.";
    }

    public Map<String, Integer> extractKeywords(String question) throws Exception {

        return wordAnalysisService.doWordAnalysis(question);
    }
}
