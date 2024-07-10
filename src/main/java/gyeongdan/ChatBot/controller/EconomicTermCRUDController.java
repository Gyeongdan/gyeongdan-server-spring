package gyeongdan.ChatBot.controller;

import gyeongdan.ChatBot.model.EconomicTerm;
import gyeongdan.ChatBot.service.EconomicTermCRUDService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/terms")
public class EconomicTermCRUDController {
    private final EconomicTermCRUDService service;
    public EconomicTermCRUDController(EconomicTermCRUDService service) {
        this.service = service;
    }


    // 경제 용어 CRUD
    @PostMapping
    public EconomicTerm addTerm(@RequestBody EconomicTerm newTerm) {
        return service.saveTerm(newTerm);
    }

    @GetMapping
    public List<EconomicTerm> getAllTerms() {
        return service.getAllTerms();
    }

    @GetMapping("/{id}")
    public Optional<EconomicTerm> getTermById(@PathVariable Long id) {
        return service.getTermById(id);
    }

    @PutMapping("/{id}")
    public EconomicTerm updateTerm(@PathVariable Long id, @RequestBody EconomicTerm updatedTerm) {
        return service.updateTerm(id, updatedTerm);
    }

    @DeleteMapping("/{id}")
    public void deleteTerm(@PathVariable Long id) {
        service.deleteTerm(id);
    }
}
