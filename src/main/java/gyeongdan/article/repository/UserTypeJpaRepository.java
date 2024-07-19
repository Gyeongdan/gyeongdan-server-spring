package gyeongdan.article.repository;

import gyeongdan.article.domain.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTypeJpaRepository extends JpaRepository<UserType, Long> {
    Optional<UserType> findByuserId(Long user_id);
}
