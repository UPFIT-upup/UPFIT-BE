package inq.upfit.dto;

import inq.upfit.domain.assignment.AssignmentType;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record AssignmentCreateRequest(
        @Nonnull Long managerId,
        @NotBlank String title,
        @Nonnull AssignmentType type,
        @Nonnull LocalDateTime startDateTime,
        @Nonnull LocalDateTime endDateTime,
        @Nonnull Boolean isAutoGraded
) {
}
