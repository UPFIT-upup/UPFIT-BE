package inq.upfit.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "user_company")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User와 다대일 관계 (User 한 명이 여러 UserCompany 가질 수 있음)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // Company와 다대일 관계 (Company 한 개가 여러 UserCompany 가질 수 있음)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    // 소속 여부
    @Column(nullable = false)
    private boolean isActive;

    // 소속 시작일
    @Column(name = "start_date")
    private LocalDate startDate;

}
