package inq.upfit.domain.master;



import inq.upfit.domain.Role;
import inq.upfit.domain.assignment.Assignment;
import inq.upfit.domain.assignment.AssignmentToUser;
import inq.upfit.domain.assignment.Submit;
import jakarta.persistence.*;
import java.util.List;
import lombok.*;
import java.time.LocalDateTime;

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

    @Column
    private Integer level;

    @Column
    private Long exp;


    /** JWT Refresh Token (optional) */
    @Column(length = 512)
    private String refreshToken;

    @OneToMany(mappedBy = "user")
    private List<Assignment> manageAssignments;

    @OneToMany(mappedBy = "user")
    private List<AssignmentToUser> assignmentsToUser;

    @OneToMany(mappedBy = "user")
    private List<Submit> submitList;
}



