package gyeongdan.chatBot.term.repository;

import gyeongdan.chatBot.term.model.EconomicTerm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EconomicTermRepository extends JpaRepository<EconomicTerm, Long> {
    Optional<EconomicTerm> findByTerm(String term);
}
