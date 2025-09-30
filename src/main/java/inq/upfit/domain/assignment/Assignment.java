package inq.upfit.domain.assignment;

import inq.upfit.domain.master.User;
import inq.upfit.dto.AssignmentCreateRequest;
import inq.upfit.dto.AssignmentUpdateRequest;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL)
    private List<AssignmentToUser> assignmentsToUser;

    @OneToOne(mappedBy = "assignment", cascade = CascadeType.ALL)
    private AssignmentInfo assignmentsInfo;

    public static Assignment of(User admin, AssignmentCreateRequest createRequest) {
        Assignment assignment = new Assignment();

        assignment.type = createRequest.assignmentType();
        assignment.submitType = createRequest.submitType();

        assignment.uploadTime = LocalDateTime.now();

        assignment.startTime = createRequest.startTime();
        assignment.endTime = createRequest.endTime();

        assignment.admin = admin;

        return assignment;
    }

    public void addAssignmentInfo(AssignmentInfo assignmentInfo) {
        this.assignmentsInfo = assignmentInfo;
    }

    public void update(AssignmentUpdateRequest updateRequest) {
        this.type = updateRequest.type();
        this.submitType = updateRequest.submitType();
        this.admin = updateRequest.admin();

        assignmentsInfo.update(updateRequest);
    }
}
