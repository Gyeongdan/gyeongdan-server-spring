package gyeongdan.ChatBot.service;

import gyeongdan.ChatBot.model.EconomicTerm;
import gyeongdan.ChatBot.repository.EconomicTermRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EconomicTermCRUDService {
    private final EconomicTermRepository repository;
    public EconomicTermCRUDService(EconomicTermRepository repository) {
        this.repository = repository;
    }

    // 경제용어 추가, 조회, 수정, 삭제
    public EconomicTerm saveTerm(EconomicTerm newTerm) {
        return repository.save(newTerm);
    }

    public List<EconomicTerm> getAllTerms() {
        return repository.findAll();
    }

    public Optional<EconomicTerm> getTermById(Long id) {
        return repository.findById(id);
    }

    public EconomicTerm updateTerm(Long id, EconomicTerm updatedTerm) {
        return repository.findById(id)
                .map(term -> {
                    term.setTerm(updatedTerm.getTerm());
                    term.setDescription(updatedTerm.getDescription());
                    return repository.save(term);
                })
                .orElseThrow(() -> new RuntimeException("Term not found with id " + id));
    }

    public void deleteTerm(Long id) {
        repository.deleteById(id);
    }
}
