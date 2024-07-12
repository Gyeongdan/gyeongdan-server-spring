package gyeongdan.ChatBot.controller;

import gyeongdan.ChatBot.service.EconomicTermService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class EconomicTermController {

    private final EconomicTermService economicTermService;
    public EconomicTermController(EconomicTermService economicTermService) {
        this.economicTermService = economicTermService;
    }

    @GetMapping("/terms/search")
    public String search(@RequestParam String question) throws Exception {

        return economicTermService.findEconomicTerm(question);
    }
}
