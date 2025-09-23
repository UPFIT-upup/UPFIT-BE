package inq.upfit.service;

import inq.upfit.domain.master.User;
import inq.upfit.dto.UserDetailDto;
import inq.upfit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDetailDto> getTeamMembers(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getTeamId() == null) {
            return List.of(); // 팀 미배정인 경우
        }

        // 같은 팀의 모든 멤버 조회
        return userRepository.findByTeamId(user.getTeamId()).stream()
                .map(UserDetailDto::from)   // User → UserDetailDto 변환
                .toList();
    }
}
