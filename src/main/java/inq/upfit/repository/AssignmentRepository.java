package inq.upfit.repository;

import inq.upfit.domain.assignment.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
}
