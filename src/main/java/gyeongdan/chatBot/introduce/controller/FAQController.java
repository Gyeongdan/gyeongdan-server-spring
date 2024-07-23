package gyeongdan.chatBot.introduce.controller;

import gyeongdan.chatBot.introduce.model.FAQ;
import gyeongdan.chatBot.introduce.service.FAQService;
import gyeongdan.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chatbot/introduce/faq")
@RequiredArgsConstructor
public class FAQController {
    private final FAQService faqService;
    @GetMapping()
    public ResponseEntity<?> findAllFAQ() {
        List<FAQ> faqs = faqService.findAllFAQ();
        
        return ResponseEntity.ok(new CommonResponse<>(faqs, "FAQ 조회 성공", true));
    }


    // CRUD
    @PostMapping("/create")
    public ResponseEntity<?> createFAQ(@RequestBody FAQ faq) {
        faqService.createFAQ(faq.getQuestion(), faq.getAnswer());

        CommonResponse<String> response = new CommonResponse<>("FAQ 생성 성공", "FAQ 생성 성공", true);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateFAQ(@RequestBody FAQ faq) {
        faqService.updateFAQ(faq.getId(), faq.getQuestion(), faq.getAnswer());

        CommonResponse<String> response = new CommonResponse<>("FAQ 수정 성공", "FAQ 수정 성공", true);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteFAQ(@RequestBody FAQ faq) {
        faqService.deleteFAQ(faq.getId());

        CommonResponse<String> response = new CommonResponse<>("FAQ 삭제 성공", "FAQ 삭제 성공", true);

        return ResponseEntity.ok(response);
    }
}
