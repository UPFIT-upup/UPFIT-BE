package inq.upfit.repository;

import inq.upfit.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {


    Optional<Company> findByUniqueCode(String code);

    Optional<Company> findByNameAndUniqueCode(String name, String code);

    boolean existsByUniqueCode(String companyCode);
}
