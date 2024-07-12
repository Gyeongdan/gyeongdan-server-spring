package gyeongdan.chatBot.repository;

import gyeongdan.chatBot.model.EconomicTerm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EconomicTermRepository extends JpaRepository<EconomicTerm, Long> {
}
