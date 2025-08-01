package inq.upfit.service;

import inq.upfit.domain.User;
import inq.upfit.domain.assignment.Assignment;
import inq.upfit.dto.AssignmentCreateRequest;
import inq.upfit.dto.AssignmentResponse;
import inq.upfit.repository.AssignmentRepository;
import inq.upfit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AssignmentService {
    private final UserRepository userRepository;
    private final AssignmentRepository assignmentRepository;

    @Transactional
    public AssignmentResponse createAssignment(AssignmentCreateRequest createRequest) {
        User manager = userRepository.findById(createRequest.managerId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 id 입니다."));

        Assignment assignment = assignmentRepository.save(Assignment.of(manager, createRequest));

        return new AssignmentResponse(assignment.getId(), assignment.getTitle());
    }
}
