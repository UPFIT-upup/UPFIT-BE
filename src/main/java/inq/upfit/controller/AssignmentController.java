package inq.upfit.controller;

import inq.upfit.domain.assignment.Assignment;
import inq.upfit.dto.AssignmentCreateRequest;
import inq.upfit.dto.AssignmentResponse;
import inq.upfit.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AssignmentController {
    private final AssignmentService assignmentService;

    @PostMapping("/api/admin/assignments")
    public ResponseEntity<AssignmentResponse> createAssignment(
            Long adminId,
            @RequestBody AssignmentCreateRequest createRequest
    ) {
        Assignment assignment = assignmentService.createAssignment(adminId, createRequest);

        return ResponseEntity.ok(AssignmentResponse.fromAssignment(assignment));
    }
}
