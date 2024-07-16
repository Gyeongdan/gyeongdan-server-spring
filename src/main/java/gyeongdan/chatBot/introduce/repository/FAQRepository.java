package gyeongdan.chatBot.introduce.repository;

import gyeongdan.chatBot.introduce.model.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FAQRepository extends JpaRepository<FAQ, Long> {
}
