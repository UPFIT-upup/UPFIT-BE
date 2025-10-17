package inq.upfit.domain.master;



import inq.upfit.domain.Role;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean isAdmin;

    @Column(nullable=false)
    private Role role;

    @Column(nullable = false)
    private String name;

    //카카오 이메일
    @Column(name = "kakao_email", nullable = false)
    private String kakaoEmail;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "team_id")
    private Long teamId;

    @Column
    private Integer level;

    @Column
    private Long exp;

    @PrePersist
    public void prePersist() {
        if (level == null) level = 1;
        if (exp == null) exp = 0L;
    }


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    @Builder.Default
    private List<Assignment> assignments = new ArrayList<>();

    //제출한 과제 수
    @Transient
    public long getSubmitCount() {
        return assignments.stream().filter(Assignment::isSubmitted).count();
    }

    //완료된 과제 수
    @Transient
    public long getCompletedCount() {
        return assignments.stream().filter(Assignment::isCompleted).count();
    }

    //전체 과제 수
    @Transient
    public int getTotalAssignments() {
        return assignments.size();
    }

    //진행률
    @Transient
    public double getProgress() {
        if(assignments.isEmpty() || assignments == null) {
            return 0.0;
        }
        return (getCompletedCount() * 100.0) / getTotalAssignments();
    }
    /** JWT Refresh Token (optional) */
    @Column(length = 512)
    private String refreshToken;





}



