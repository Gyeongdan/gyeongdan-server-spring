package gyeongdan.shortform.repository;

import gyeongdan.shortform.domain.ShortForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShortFormJpaRepository extends JpaRepository<ShortForm, Long> {

    @Query(value = "SELECT * FROM gyeongdan.api_visualization sf WHERE sf.id != (SELECT MAX(sf2.id) FROM gyeongdan.api_visualization sf2) ORDER BY sf.created_at DESC LIMIT 3", nativeQuery = true)
    List<ShortForm> findTop3AfterLatest();
}
