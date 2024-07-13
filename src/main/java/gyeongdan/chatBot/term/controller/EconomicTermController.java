package gyeongdan.chatBot.term.controller;

import gyeongdan.chatBot.term.service.EconomicTermService;
import gyeongdan.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/chatbot/terms")
@RequiredArgsConstructor
public class EconomicTermController {
    private final EconomicTermService economicTermService;

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestBody String question) throws Exception {
        Map<String, String> data = economicTermService.findEconomicTerm(question);

        // 실패
        if (data == null) {
            CommonResponse<Map<String, String>> response = new CommonResponse<>(null, "경제 용어 찾기 실패", false);
            return ResponseEntity.ok(response);
        }

        // 성공
        CommonResponse<Map<String, String>> response = new CommonResponse<>(data, "경단어 찾기 성공", true);
        return ResponseEntity.ok(response);
    }
}
