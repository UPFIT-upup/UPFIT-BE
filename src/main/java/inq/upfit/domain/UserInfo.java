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
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(name = "tel_no", length = 20, nullable = false)
    private String telNo;

    @Enumerated(EnumType.STRING)
    @Column
    private Grade grade;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(name = "join_date", nullable = false)
    private LocalDateTime joinDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}
