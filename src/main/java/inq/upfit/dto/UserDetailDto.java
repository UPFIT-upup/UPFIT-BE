package inq.upfit.dto;

import inq.upfit.domain.master.Assignment;
import inq.upfit.domain.master.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailDto {
    private Long userId;
    private String name;
    private double progress;
    //private String status;

    private List<AssignmentDto> assignments;

    public static UserDetailDto from(User user) {
        List<AssignmentDto> assignmentDtos = user.getAssignments() != null ? user.getAssignments().stream().map(AssignmentDto::from).toList() : new ArrayList<>();
        double progress = 0.0;
        if (!assignmentDtos.isEmpty()) {
            long completed = assignmentDtos.stream().filter(AssignmentDto::isCompleted).count();
            progress = (completed * 100.0) / assignmentDtos.size();
        }

        return UserDetailDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .assignments(assignmentDtos)
                .progress(progress)
                .build();
    }
}
