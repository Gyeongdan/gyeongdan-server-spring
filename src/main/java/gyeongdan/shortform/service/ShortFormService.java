package gyeongdan.shortform.service;

import gyeongdan.shortform.domain.ShortForm;
import gyeongdan.shortform.repository.ShortFormJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
