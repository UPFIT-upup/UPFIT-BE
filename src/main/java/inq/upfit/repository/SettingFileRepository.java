package inq.upfit.repository;

import inq.upfit.domain.tenant.SettingFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SettingFileRepository extends JpaRepository<SettingFile, Long> {

    List<SettingFile> findByCompanyId(Long companyId);

    }