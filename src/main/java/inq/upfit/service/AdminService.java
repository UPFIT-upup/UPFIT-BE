package inq.upfit.service;

import inq.upfit.domain.Role;
import inq.upfit.domain.master.Assignment;
import inq.upfit.domain.master.User;
import inq.upfit.dto.UserDetailDto;
import inq.upfit.exception.UserNotFoundException;
import inq.upfit.repository.AssignmentRepository;
import inq.upfit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final AssignmentRepository assignmentRepository;

    public List<User> getAllUsers() {
        return userRepository.findByRole(Role.MEMBER);
    }

    public UserDetailDto getUserById(Long id) {
        return userRepository.findById(id).map(UserDetailDto::from)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User updateUserPartial(Long id, UserDetailDto dto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));

        // ⚙️ 필요한 필드만 업데이트 (null 체크 포함)
        if (dto.getName() != null) {
            existingUser.setName(dto.getName());
        }
        if (dto.getRole() != null) {
            existingUser.setRole(dto.getRole());
        }
        if (dto.getTeamId() != null) {
            existingUser.setTeamId(dto.getTeamId());
        }
        if (dto.getLevel() != null) {
            existingUser.setLevel(dto.getLevel());
        }
        if (dto.getExp() != null) {
            existingUser.setExp(dto.getExp());
        }
        return userRepository.save(existingUser);
    }


    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)) {
            throw new UserNotFoundException("Cannot delete. User not found with id " + id);
        }
        userRepository.deleteById(id);
    }

    public List<Assignment> getAssignmentsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("Assignments cannot be fetched. User not found with id " + userId);
        }
        return assignmentRepository.findByUserId(userId);
    }
}
