package gyeongdan.user.repository;

import gyeongdan.user.domain.UserType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTypeJpaRepository extends JpaRepository<UserType, Long> {
    Optional<UserType> findTopByUserIdOrderByIdDesc(Long userId);

    Optional<List<UserType>> findAllByUserId(Long userId);
}
