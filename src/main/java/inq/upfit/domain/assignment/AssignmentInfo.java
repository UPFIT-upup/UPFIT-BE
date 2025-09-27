package inq.upfit.domain.assignment;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
//과제 문제 관련 디비
public class AssignmentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Difficulty difficulty;

    private String title;
    private String content;

    private String fileAddress;

    //모범답안 (자동채점 안함 그냥 채점시 참고하게 제공)
    private String answer;

    //과제 연관관계 매핑 필요
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASSIGNMENT_ID", nullable = false)
    private Assignment assignment;

}
