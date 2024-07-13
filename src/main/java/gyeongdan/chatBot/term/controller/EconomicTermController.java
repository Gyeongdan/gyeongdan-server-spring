package gyeongdan.chatBot.term.controller;

import gyeongdan.chatBot.term.service.EconomicTermService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/chatbot/terms")
@RequiredArgsConstructor
public class EconomicTermController {
    private final EconomicTermService economicTermService;

    @GetMapping("/search")
    public String search(@RequestParam String question) throws Exception {
        return economicTermService.findEconomicTerm(question);
    }
}
