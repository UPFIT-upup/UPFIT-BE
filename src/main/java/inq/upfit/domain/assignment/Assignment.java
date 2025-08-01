package inq.upfit.domain.assignment;

import inq.upfit.domain.User;
import inq.upfit.dto.AssignmentCreateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Assignment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    private String title;

    @Enumerated(EnumType.STRING)
    private AssignmentType type;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private boolean isAutoGraded;

    public static Assignment of(User manager, AssignmentCreateRequest createRequest) {
        Assignment assignment = new Assignment();

        assignment.manager = manager;
        assignment.title = createRequest.title();
        assignment.type = createRequest.type();
        assignment.startDateTime = createRequest.startDateTime();
        assignment.endDateTime = createRequest.endDateTime();
        assignment.isAutoGraded = createRequest.isAutoGraded();

        return assignment;
    }
}
