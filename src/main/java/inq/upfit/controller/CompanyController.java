package inq.upfit.controller;

import inq.upfit.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyRepository companyRepository;

    @GetMapping("/generate-code")
    public ResponseEntity<String> generateUniqueCompanyCode() {
        String code;
        do {
            code = UUID.randomUUID().toString().substring(0, 8);
        } while (companyRepository.existsByUniqueCode(code));

        return ResponseEntity.ok(code);
    }
}
