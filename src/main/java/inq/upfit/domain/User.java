package inq.upfit.domain;



import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "register_at", nullable = false)
    private LocalDateTime registerAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Provider provider;

    @Column(name = "kakao_id", nullable = false)
    private String kakaoId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Grade grade;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(name = "tel_no", length = 20, nullable = false)
    private String telNo;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(name = "join_date", nullable = false)
    private LocalDateTime joinDate;

    @Column(nullable = false)
    private Integer level;

    @Column(nullable = false)
    private Long exp;
}



