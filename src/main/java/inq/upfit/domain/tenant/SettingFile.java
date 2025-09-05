package inq.upfit.domain.tenant;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class SettingFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileUrl;

    @Column(nullable = false)
    //회사별 개별 DB에 소속될 엔티티기에, 외래키 제약 없이 단순 식별자용 컬럼
    private Long companyId;


}
