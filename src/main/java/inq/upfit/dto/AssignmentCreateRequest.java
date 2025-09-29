package inq.upfit.dto;

import inq.upfit.domain.assignment.AssignmentType;
import inq.upfit.domain.assignment.Difficulty;
import inq.upfit.domain.assignment.SubmitType;

import java.time.LocalDateTime;

public record AssignmentCreateRequest(
        AssignmentType assignmentType,
        SubmitType submitType,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Difficulty difficulty,
        String title,
        String content,
        String fileAddress
) {
}
