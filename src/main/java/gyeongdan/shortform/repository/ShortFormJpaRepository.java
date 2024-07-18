package gyeongdan.shortform.repository;

import gyeongdan.shortform.domain.ShortForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShortFormJpaRepository extends JpaRepository<ShortForm, Long> {
    List<ShortForm> findTop3ByOrderByCreatedAtDesc();
}
