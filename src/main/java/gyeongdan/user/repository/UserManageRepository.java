package gyeongdan.user.repository;

import gyeongdan.user.domain.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserManageRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByKakaoUserId(Long kakaoUserId);
}
