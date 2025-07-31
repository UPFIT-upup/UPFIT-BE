package inq.upfit.repository;

import inq.upfit.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByKakaoId(String kakaoId); // 단일 계정 조회
    List<User> findAllByKakaoId(String kakaoId);

}
