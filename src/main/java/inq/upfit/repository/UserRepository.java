package inq.upfit.repository;

import inq.upfit.domain.Role;
import inq.upfit.domain.master.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByKakaoEmail(String kakaoEmail);

    Optional<User> findByRefreshToken(String email);

    List<User> findByRole(Role role);

    List<User> findByTeamId(Long teamId);
}
