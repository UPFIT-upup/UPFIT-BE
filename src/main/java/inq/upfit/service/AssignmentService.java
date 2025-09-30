package inq.upfit.service;

import inq.upfit.domain.assignment.Assignment;
import inq.upfit.domain.assignment.AssignmentInfo;
import inq.upfit.domain.master.User;
import inq.upfit.dto.AssignmentCreateRequest;
import inq.upfit.dto.AssignmentUpdateRequest;
import inq.upfit.repository.AssignmentRepository;
import inq.upfit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AssignmentService {
    private final UserRepository userRepository;
    private final AssignmentRepository assignmentRepository;

    public Assignment createAssignment(Long adminId, AssignmentCreateRequest createRequest) {
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Assignment assignment = Assignment.of(admin, createRequest);
        AssignmentInfo assignmentInfo = AssignmentInfo.of(createRequest);
        assignmentInfo.setAssignment(assignment);

        return assignmentRepository.save(assignment);
    }

    public Assignment findAssignment(Long assignmentId) {
        return assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));
    }

    public Assignment updateAssignment(Long assignmentId, AssignmentUpdateRequest updateRequest) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        assignment.update(updateRequest);
        return assignmentRepository.save(assignment);
    }
}
