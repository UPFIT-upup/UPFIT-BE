package inq.upfit.repository;

import inq.upfit.domain.Company;
import inq.upfit.domain.User;
import inq.upfit.domain.UserCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCompanyRepository extends JpaRepository<UserCompany, Long> {
    List<UserCompany> findAllByUser(User user);

    boolean existsByUserAndCompany(User user, Company company);
}
