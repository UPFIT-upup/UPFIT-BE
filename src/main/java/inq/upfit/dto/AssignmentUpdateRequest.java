package inq.upfit.dto;

import inq.upfit.domain.assignment.AssignmentType;
import inq.upfit.domain.assignment.Difficulty;
import inq.upfit.domain.assignment.SubmitType;
import inq.upfit.domain.master.User;

public record AssignmentUpdateRequest(
        AssignmentType type,
        SubmitType submitType,
        User admin,
        Difficulty difficulty,
        String title,
        String content,
        String fileAddress
) {
}
