package inq.upfit.controller;

import inq.upfit.domain.master.User;
import inq.upfit.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/students")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // 학생 목록 조회
    @GetMapping
    public List<User> getUsers() {
        return adminService.getAllUsers();
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
}