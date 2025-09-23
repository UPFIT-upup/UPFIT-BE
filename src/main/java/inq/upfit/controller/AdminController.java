package inq.upfit.controller;

import inq.upfit.domain.master.User;
import inq.upfit.dto.UserDetailDto;
import inq.upfit.service.AdminService;
import inq.upfit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/students")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;

    // 학생 목록 조회
    @GetMapping
    public List<User> getUsers() {
        return adminService.getAllUsers();
    }

    // 학생 정보 세부 조회
    @GetMapping("/{id}")
    public UserDetailDto getUser(@PathVariable Long id) {
        return adminService.getUserById(id);
    }

    // 학생 추가
    @PostMapping
    public User addUser(@RequestBody User user) {
        return adminService.addUser(user);
    }

    // 학생 삭제
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        adminService.deleteUser(id);
    }

    // 같은 팀원 조회
    @GetMapping("/{id}/team-members")
    public List<UserDetailDto> getTeamMembers(@PathVariable Long id) { return userService.getTeamMembers(id); }

}