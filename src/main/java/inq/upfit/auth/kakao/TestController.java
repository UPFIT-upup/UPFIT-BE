package inq.upfit.auth.kakao;

import inq.upfit.auth.utils.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    //카카오 로그인 후 테스트 컨트롤러

    @PostMapping("/test")


    public String getCurrentUser(@AuthenticationPrincipal UserDetailsImpl user) {
        if (user == null) {
            return "인증된 사용자가 없습니다.";
        }
        return "현재 로그인한 사용자: " + user.getUsername() +
                " / 권한: " + user.getAuthorities();
    }
}
