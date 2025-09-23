package inq.upfit.config;

import inq.upfit.domain.master.Department;
import inq.upfit.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/** (테스트용)
 * 애플리케이션 시작 시점에 초기 데이터를 생성하는 클래스입니다.
 * CommandLineRunner 인터페이스를 구현하여 run() 메서드에 로직을 작성합니다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;

    @Override
    public void run(String... args) throws Exception {
        if (departmentRepository.count() == 0) {
            log.info("테스트용 부서 더미 데이터를 생성합니다...");
            departmentRepository.save(new Department(null, "개발팀", null));
            departmentRepository.save(new Department(null, "기획팀", null));
            departmentRepository.save(new Department(null, "디자인팀", null));
            departmentRepository.save(new Department(null, "마케팅팀", null));
            log.info("부서 더미 데이터 생성이 완료되었습니다.");
        } else {
            log.info("이미 부서 데이터가 존재하므로 더미 데이터를 생성하지 않습니다.");
        }
    }
}

