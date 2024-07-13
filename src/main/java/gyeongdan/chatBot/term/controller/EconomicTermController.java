package gyeongdan.chatBot.term.controller;

import gyeongdan.chatBot.term.service.EconomicTermService;
import gyeongdan.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/chatbot/terms")
@RequiredArgsConstructor
public class EconomicTermController {
    private final EconomicTermService economicTermService;

    @GetMapping("/search")
    public CommonResponse<Map<String, String>> search(@RequestBody String question) throws Exception {
        return economicTermService.findEconomicTerm(question);
    }
}
