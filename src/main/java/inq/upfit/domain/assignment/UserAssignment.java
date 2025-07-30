package inq.upfit.domain.assignment;

import inq.upfit.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @ManyToOne
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;

    private boolean isSubmitted;

    private int grade;

    private LocalDateTime assignedDateTime;

    private LocalDateTime gradedDateTime;
}
