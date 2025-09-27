package inq.upfit.domain.assignment;

import inq.upfit.domain.master.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Entity
@Getter
//과제 공통정보 디비
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private AssignmentType type;

    private SubmitType submitType;

    private LocalDateTime uploadTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    //유저, 부서 연관관계 매핑 필요
    //부서를 굳이 할당해야할까? 일단 관리자 유저만 매핑

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User admin;

    @OneToMany(mappedBy = "assignment")
    private List<AssignmentToUser> assignmentsToUser;

    @OneToMany(mappedBy = "assignment")
    private List<AssignmentInfo> assignmentsInfo;
}
