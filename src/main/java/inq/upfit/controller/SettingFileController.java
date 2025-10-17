package inq.upfit.controller;


import inq.upfit.auth.utils.UserDetailsImpl;
import inq.upfit.service.SettingFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/*
@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class SettingFileController {

    private final SettingFileService fileService;


    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Long userId = userDetails.getId();

        // user가 속한 회사 중 company-owner 권한 회사 찾기



        String fileUrl = fileService.uploadToStorage(file);
        fileService.saveFileUrl(companyId, fileUrl);

        return ResponseEntity.ok("File uploaded: " + fileUrl);
    }
}
*/


