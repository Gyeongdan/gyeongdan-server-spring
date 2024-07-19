package gyeongdan.article.repository;

import gyeongdan.article.domain.Recommends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendJpaRepository extends JpaRepository<Recommends, Long> {
}
