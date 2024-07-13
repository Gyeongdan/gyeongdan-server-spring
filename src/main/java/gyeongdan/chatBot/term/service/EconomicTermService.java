package gyeongdan.chatBot.term.service;

import gyeongdan.chatBot.term.model.EconomicTerm;
import gyeongdan.chatBot.term.repository.EconomicTermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EconomicTermService {

    private final EconomicTermRepository economicTermRepository;
    private final WordAnalysisService wordAnalysisService;
    
    public String findEconomicTerm(String question) throws Exception {
        Map<String, Integer> kor_keywords = extractKeywords(question);


        List<EconomicTerm> terms = economicTermRepository.findAll();


        // 질문 그자체
        for (EconomicTerm term : terms) {
            if (term.getTerm().equalsIgnoreCase(question)) {
                return term.getDescription();
            }
        }

        // 한국어 형태소 분석기와 비교
        for (EconomicTerm term : terms) {
            // 경제 용어 객체의 term 필드와 keywords 리스트의 요소들을 비교하여 일치하는지 확인
            for (String keyword : kor_keywords.keySet()) {
                if (term.getTerm().equalsIgnoreCase(keyword)) {
                    return formatResponse(term);
                }
            }
        }

        // 영어 단어 분석기와 비교


        return "죄송합니다! 질문에서 관련된 경제 용어를 찾을 수 없습니다.";
    }

    private String formatResponse(EconomicTerm term) {
        return String.format("%s에 대해 설명해드리겠습니다! %s ex) %s",
                term.getTerm(), term.getDescription(), term.getExample());
    }


    public Map<String, Integer> extractKeywords(String question) throws Exception {

        return wordAnalysisService.doWordAnalysis(question);
    }
}
