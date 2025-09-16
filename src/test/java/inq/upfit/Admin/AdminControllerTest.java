package inq.upfit.Admin;

import inq.upfit.auth.jwt.JwtProvider;
import inq.upfit.domain.Role;
import inq.upfit.domain.master.Assignment;
import inq.upfit.domain.master.User;
import inq.upfit.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@Rollback
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserRepository userRepository;

    private String adminToken;

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

        // JWT 생성
        adminToken = jwtProvider.generateTokenDto(
                new UsernamePasswordAuthenticationToken(
                        adminUser.getKakaoEmail(),
                        null,
                        List.of(new SimpleGrantedAuthority(adminUser.getRole().name()))
                )
        ).getAccessToken();
    }

    @Test
    @DisplayName("학생 추가 API 테스트")
    void addUser() throws Exception {
        String userJson = """
            {
                "kakaoEmail": "newuser@test.com",
                "name": "New User",
                "role": "MEMBER"
                }
            """;

        mockMvc.perform(post("/api/admin/students")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("학생 삭제 API 테스트")
    void deleteUser() throws Exception {
        User user = User.builder()
                .kakaoEmail("todelete@test.com")
                .name("To Delete")
                .role(Role.MEMBER)
                .build();
        userRepository.saveAndFlush(user);

        mockMvc.perform(delete("/api/admin/students/" + user.getId())
                        .header("Authorization", "Bearer " + adminToken)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("학생 전체 조회 API 테스트")
    void getAllUsers() throws Exception {
        // given - 테스트용 학생 2명 추가
        User user1 = userRepository.saveAndFlush(User.builder()
                .kakaoEmail("student1@test.com")
                .name("Student One")
                .role(Role.MEMBER)
                .assignments(new ArrayList<>())
                .build());

        User user2 = userRepository.saveAndFlush(User.builder()
                .kakaoEmail("student2@test.com")
                .name("Student Two")
                .role(Role.MEMBER)
                .assignments(new ArrayList<>())
                .build());

        // when & then
        mockMvc.perform(get("/api/admin/students")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[1].name").exists());
    }

    @Test
    @DisplayName("학생 상세 조회 API 테스트")
    void getUserById() throws Exception {
        // given - 테스트용 학생 1명 추가
        User user = userRepository.saveAndFlush(User.builder()
                .kakaoEmail("detail@test.com")
                .name("Detail User")
                .role(Role.MEMBER)
                .assignments(new ArrayList<>())
                .build());

        // when & then
        mockMvc.perform(get("/api/admin/students/" + user.getId())
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Detail User"));
    }

    @Test
    @DisplayName("학생 상세 조회 - 과제 포함 API 테스트")
    void getUserById_withAssignments() throws Exception {
        // given - user 생성
        User user = User.builder()
                .kakaoEmail("detail_assign@test.com")
                .name("Detail With Assign")
                .role(Role.MEMBER)
                .assignments(new ArrayList<>())
                .build();

        user = userRepository.saveAndFlush(user);

        // 과제 2개 생성 (1개 완료 -> progress 50.0)
        Assignment a1 = Assignment.builder()
                .title("1주차 과제")
                .dueDate(LocalDate.of(2025, 10, 23))
                .submitted(true)
                .completed(true)
                .build();

        Assignment a2 = Assignment.builder()
                .title("2주차 과제")
                .dueDate(LocalDate.of(2025, 10, 30))
                .submitted(false)
                .completed(false)
                .build();

        a1.setUser(user);
        a2.setUser(user);
        user.getAssignments().add(a1);
        user.getAssignments().add(a2);

        // 저장 (cascade=ALL 이면 user만 저장해도 되지만 안전하게 flush)
        userRepository.saveAndFlush(user);

        // when & then
        mockMvc.perform(get("/api/admin/students/" + user.getId())
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Detail With Assign"))
                .andExpect(jsonPath("$.assignments").isArray())
                .andExpect(jsonPath("$.assignments[0].title").value("1주차 과제"))
                .andExpect(jsonPath("$.assignments.length()").value(2))
                .andExpect(jsonPath("$.progress").value(50.0));
    }


}