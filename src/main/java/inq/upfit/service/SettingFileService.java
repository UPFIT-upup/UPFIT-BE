package inq.upfit.service;


import inq.upfit.domain.master.Company;
import inq.upfit.domain.tenant.SettingFile;
import inq.upfit.repository.CompanyRepository;
import inq.upfit.repository.SettingFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SettingFileService {

    private final SettingFileRepository settingFileRepository;
    private final CompanyRepository companyRepository;





    //클라우드 스토리지에 저장
    public String uploadToStorage(MultipartFile file){

        String fileUrl= "~" + file.getOriginalFilename();

        return fileUrl;
    }

    public void saveFileUrl(Long companyId, String fileUrl){
        Company company = companyRepository.findById(companyId)
                .orElseThrow(()-> new RuntimeException("회사를 찾을수 없습니다"));

        SettingFile settingFile = SettingFile.builder()
                .companyId(companyId)
                .fileUrl(fileUrl).
                build();

        settingFileRepository.save(settingFile);
    }

    public List<String> getFilesByCompany(Long companyId){
        return settingFileRepository.findByCompanyId(companyId)
                .stream().map(SettingFile::getFileUrl).toList();
    }
}

