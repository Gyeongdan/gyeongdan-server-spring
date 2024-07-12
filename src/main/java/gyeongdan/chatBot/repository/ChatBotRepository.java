package gyeongdan.chatBot.repository;

import gyeongdan.chatBot.model.GptResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatBotRepository extends JpaRepository<GptResponse, Long> {
}
