package gyeongdan.chatBot.introduce.repository;

import gyeongdan.chatBot.introduce.model.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FAQJPArepository extends JpaRepository<FAQ, Long> {
    Optional<FAQ> findByQuestionAndAnswer(String question, String answer);
}
