package inq.upfit.domain;



import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
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

    @Column(name = "register_at", nullable = false)
    private LocalDateTime registerAt;

    @Enumerated(EnumType.STRING)
    @Column
    private Provider provider;

    //카카오 아이디가 있어도, 새로 회원가입 가능함.
    @Column(name = "kakao_id", nullable = false)
    private String kakaoId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SystemRole systemRole;


    @Column
    private Integer level;

    @Column
    private Long exp;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserCompany> userCompanies;


    /** JWT Refresh Token (optional) */
    @Column(length = 512)
    private String refreshToken;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserInfo userInfo;



}



