package inq.upfit.Admin;

import inq.upfit.auth.jwt.JwtProvider;
import inq.upfit.domain.Role;
import inq.upfit.domain.master.User;
import inq.upfit.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@Rollback
public class AdminControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserRepository userRepository;

    private String adminToken;
    private String memberToken;

    @BeforeEach
    void setUp() {
        // 테스트용 ADMIN 유저 생성
        User adminUser = User.builder()
                .kakaoEmail("admin@test.com")
                .name("Admin User")
                .role(Role.ADMIN)
                .build();

        // 테스트용 MEMBER 유저 생성
        User memberUser = User.builder()
                .kakaoEmail("member@test.com")
                .name("Member User")
                .role(Role.MEMBER)
                .assignments(new ArrayList<>())
                .build();

        // 저장 후 엔티티를 다시 가져오기 (ID 자동 생성)
        adminUser = userRepository.saveAndFlush(adminUser);
        memberUser = userRepository.saveAndFlush(memberUser);

        // JWT 생성
        adminToken = jwtProvider.generateTokenDto(
                new UsernamePasswordAuthenticationToken(
                        adminUser.getKakaoEmail(),
                        null,
                        List.of(new SimpleGrantedAuthority(adminUser.getRole().name()))
                )
        ).getAccessToken();

        memberToken = jwtProvider.generateTokenDto(
                new UsernamePasswordAuthenticationToken(
                        memberUser.getKakaoEmail(),
                        null,
                        List.of(new SimpleGrantedAuthority(memberUser.getRole().name()))
                )
        ).getAccessToken();
    }

    @Test
    void adminCanAccessAdminApi() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/students")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void memberCannotAccessAdminApi() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/students")
                        .header("Authorization", "Bearer " + memberToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()); // 접근 권한 없음 403
    }

    @Test
    void unauthenticatedCannotAccessAdminApi() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/students")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized()); // 인증 되지 않음 401
    }

}
