package inq.upfit.dto;

import inq.upfit.domain.master.Assignment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignmentDto {
    private String title;
    private LocalDate deadLine;
    private boolean submitted;
    private boolean completed;

    public static AssignmentDto from(Assignment assignment) {
        return AssignmentDto.builder()
                .title(assignment.getTitle())
                .submitted(assignment.isSubmitted())
                .completed(assignment.isCompleted())
                .build();
    }
}
