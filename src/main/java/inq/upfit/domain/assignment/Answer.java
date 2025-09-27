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

//과제제출내역에서 입력한 답을 가지는 디비
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String fileAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUBMIT_ID", nullable = false)
    private Submit submit;
}
