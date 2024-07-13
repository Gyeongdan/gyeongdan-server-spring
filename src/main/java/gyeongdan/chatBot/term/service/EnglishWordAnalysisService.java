package gyeongdan.chatBot.term.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EnglishWordAnalysisService {
    private static final Pattern ENGLISH_WORD_PATTERN = Pattern.compile("\\b[A-Za-z]+\\b");

    public Map<String, Integer> extractEngWords(String question) {
        Map<String, Integer> engKeywords = new HashMap<>();

        // 정규 표현식을 사용하여 영어 단어를 추출
        String[] parts = question.split("[^A-Za-z]+");
        for (String part : parts) {
            Matcher matcher = ENGLISH_WORD_PATTERN.matcher(part);
            while (matcher.find()) {
                String word = matcher.group();
                System.out.println("word: " + word);
                engKeywords.put(word, engKeywords.getOrDefault(word, 0) + 1);
            }
        }

        System.out.println("engKeywords: " + engKeywords);
        return engKeywords;
    }
}
