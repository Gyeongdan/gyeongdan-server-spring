package gyeongdan.shortform.controller;

import gyeongdan.shortform.domain.ShortForm;
import gyeongdan.shortform.dto.ShortFormDetailResponse;
import gyeongdan.shortform.service.ShortFormService;
import gyeongdan.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/shortform")
@RequiredArgsConstructor
public class ShortFormController {
    private final ShortFormService shortFormService;

    // 숏폼 전체 조회
    @GetMapping("")
    public ResponseEntity<?> getShortForms() {
        List<ShortForm> shortForms = shortFormService.getShortForms();
        return ResponseEntity.ok(new CommonResponse<>(shortForms, "숏폼 조회 성공", true));
    }
    // 숏폼 상세 조회
    @GetMapping("/detail")
    public ResponseEntity<?> getShortForm(@RequestParam Long id) {
        ShortForm shortForm = shortFormService.getShortForm(id);
        return ResponseEntity.ok(new CommonResponse<>(new ShortFormDetailResponse(shortForm), "숏폼 조회 성공", true));
    }

    // 숏폼 html 상세 조회
    @GetMapping("html")
    public ResponseEntity<?> getShortFormHtml(@RequestParam Long id) {
        ShortForm shortForm = shortFormService.getShortForm(id);
        return ResponseEntity.ok(new CommonResponse<>(shortForm.getGraph_html(), "숏폼 조회 성공", true));
    }
}
