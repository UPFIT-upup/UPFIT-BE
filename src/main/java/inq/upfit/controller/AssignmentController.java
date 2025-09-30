package inq.upfit.controller;

import inq.upfit.domain.assignment.Assignment;
import inq.upfit.dto.AssignmentCreateRequest;
import inq.upfit.dto.AssignmentResponse;
import inq.upfit.dto.AssignmentUpdateRequest;
import inq.upfit.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/admin/assignments")
@RequiredArgsConstructor
@RestController
public class AssignmentController {
    private final AssignmentService assignmentService;

    @PostMapping
    public ResponseEntity<AssignmentResponse> createAssignment(
            Long adminId,
            @RequestBody AssignmentCreateRequest createRequest
    ) {
        Assignment assignment = assignmentService.createAssignment(adminId, createRequest);

        return ResponseEntity.ok(AssignmentResponse.fromAssignment(assignment));
    }

    @GetMapping("/{assignmentId}")
    public ResponseEntity<AssignmentResponse> getAssignment(@PathVariable Long assignmentId) {
        Assignment assignment = assignmentService.findAssignment(assignmentId);

        return ResponseEntity.ok(AssignmentResponse.fromAssignment(assignment));
    }

    @PutMapping("/{assignmentId}")
    public ResponseEntity<AssignmentResponse> updateAssignment(
            @PathVariable Long assignmentId,
            @RequestBody AssignmentUpdateRequest updateRequest
    ) {
        Assignment assignment = assignmentService.updateAssignment(assignmentId, updateRequest);

        return ResponseEntity.ok(AssignmentResponse.fromAssignment(assignment));
    }
}
