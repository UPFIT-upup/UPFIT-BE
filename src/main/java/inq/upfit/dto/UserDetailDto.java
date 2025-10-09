package inq.upfit.dto;

import inq.upfit.domain.Role;
import inq.upfit.domain.master.Assignment;
import inq.upfit.domain.master.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UserDetailDto {
    private String name;
    private Role role;
    private Long teamId;
    private Integer level;
    private Long exp;
    private double progress;


    private List<AssignmentDto> assignments;

    public static UserDetailDto from(User user) {
        List<AssignmentDto> assignmentDtos = user.getAssignments() != null ? user.getAssignments().stream().map(AssignmentDto::from).toList() : new ArrayList<>();
        double progress = 0.0;
        if (!assignmentDtos.isEmpty()) {
            long completed = assignmentDtos.stream().filter(AssignmentDto::isCompleted).count();
            progress = (completed * 100.0) / assignmentDtos.size();
        }

        return UserDetailDto.builder()
                .name(user.getName())
                .role(user.getRole())
                .assignments(assignmentDtos)
                .teamId(user.getTeamId())
                .level(user.getLevel())
                .exp(user.getExp())
                .progress(progress)
                .build();
    }
}
