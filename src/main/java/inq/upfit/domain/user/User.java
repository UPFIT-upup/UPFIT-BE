package inq.upfit.domain.user;

import inq.upfit.domain.assignment.Assignment;
import inq.upfit.domain.assignment.UserAssignment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "users")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    private List<Assignment> createdAssignments = new ArrayList<>();

    @OneToMany(mappedBy = "assignee", fetch = FetchType.LAZY)
    private List<UserAssignment> assignedAssignments = new ArrayList<>();
}
