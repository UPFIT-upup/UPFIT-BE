package inq.upfit.domain.assignment;


import inq.upfit.domain.master.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.Getter;

@Entity
@Getter
//유저 과제 할당 디비
public class AssignmentToUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isSubmit;

    private int score;

    private LocalDateTime allocatedTime;
    private LocalDateTime submittedTime;
    private LocalDateTime scoredTime;

    //과제, 회원, 제출 디비 매핑 필요
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "ASSIGNMENT_ID", nullable = false)
    private Assignment assignment;

    @OneToOne
    @JoinColumn(name = "SUBMIT_ID")
    private Submit submit;

}
