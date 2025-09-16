package inq.upfit.service;

import inq.upfit.domain.Role;
import inq.upfit.domain.master.Assignment;
import inq.upfit.domain.master.User;
import inq.upfit.dto.UserDetailDto;
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
        return userRepository.findById(id).map(UserDetailDto::from).orElse(null);
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<Assignment> getAssignmentsByUserId(Long userId) {
        return assignmentRepository.findByUserId(userId);
    }
}
