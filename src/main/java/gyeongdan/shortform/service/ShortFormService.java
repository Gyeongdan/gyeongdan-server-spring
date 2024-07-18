package gyeongdan.shortform.service;

import gyeongdan.shortform.domain.ShortForm;
import gyeongdan.shortform.dto.ShortFormDetailResponse;
import gyeongdan.shortform.repository.ShortFormJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShortFormService {
    private final ShortFormJpaRepository shortFormRepository;

    // 숏폼 전체 조회
    public List<ShortForm> getShortForms() {
        return shortFormRepository.findAll();
    }


    // 숏폼 상세 조회
    public ShortForm getShortForm(Long id) {
        return shortFormRepository.findById(id).orElse(null);
    }

    // 가장 최근 숏폼 3개 가져오기
    public List<ShortFormDetailResponse> getRecentShortForms() {
        List<ShortForm> shortForms = shortFormRepository.findTop3ByOrderByCreatedAtDesc();

        return shortForms.stream()
                .map(ShortFormDetailResponse::new)
                .collect(Collectors.toList());
    }
}
