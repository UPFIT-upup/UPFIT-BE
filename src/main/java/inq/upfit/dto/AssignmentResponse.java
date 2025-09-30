package inq.upfit.dto;

import inq.upfit.domain.assignment.Assignment;
import inq.upfit.domain.master.User;

public record AssignmentResponse(
        Long id,
        User admin
) {
    public static AssignmentResponse fromAssignment(Assignment assignment) {
        return new AssignmentResponse(assignment.getId(), assignment.getAdmin());
    }
}
