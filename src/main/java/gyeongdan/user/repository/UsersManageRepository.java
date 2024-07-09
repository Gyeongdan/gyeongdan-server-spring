package gyeongdan.user.repository;

import gyeongdan.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersManageRepository extends JpaRepository<Users, Long> {

}
