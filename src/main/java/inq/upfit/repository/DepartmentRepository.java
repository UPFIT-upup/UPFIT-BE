package inq.upfit.repository;

import inq.upfit.domain.master.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}