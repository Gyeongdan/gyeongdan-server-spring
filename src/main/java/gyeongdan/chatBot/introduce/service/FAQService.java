package gyeongdan.chatBot.introduce.service;

import gyeongdan.chatBot.introduce.model.FAQ;
import gyeongdan.chatBot.introduce.repository.FAQRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FAQService {
    private final FAQRepository faqRepository;
    public void createFAQ(String question, String answer) {
        if(checkDuplication(question, answer)) {
            throw new IllegalArgumentException("중복된 질문이 존재합니다.");
        }

        faqRepository.save(FAQ.builder().question(question).answer(answer).build());
    }

    public List<FAQ> findAllFAQ() {
        return faqRepository.findAll();
    }

    public void updateFAQ(Long id, String question, String answer) {
        Optional<FAQ> faq = faqRepository.findById(id);
        if (faq.isPresent()) {
            faq.get().setQuestion(question);
            faq.get().setAnswer(answer);
            faqRepository.save(faq.get());
        }
    }

    public void deleteFAQ(Long id) {
        faqRepository.deleteById(id);
    }

    public boolean checkDuplication(String question, String answer) {
        FAQ faq = faqRepository.findByQuestionAndAnswer(question, answer).orElse(null);

        if (faq == null) {
            return false;
        }

        return true;
    }
}
