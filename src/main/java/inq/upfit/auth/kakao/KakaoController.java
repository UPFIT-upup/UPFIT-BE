package inq.upfit.auth.kakao;

import inq.upfit.auth.utils.UserDetailsImpl;
import inq.upfit.dto.SignupRequest;
import inq.upfit.dto.TokenDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "KakaoController", description = "카카오 로그인 및 회원가입 API")
public class KakaoController {

    private final KaKaoService kakaoService;





    // 🔍 code를 브라우저로 받기 위한 디버그 엔드포인트
    @GetMapping("/kakao/test")
    public ResponseEntity<String> testCode(@RequestParam("code") String code) {
        return ResponseEntity.ok("받은 인가 코드: " + code);
    }



    @Operation(summary = "카카오 로그인 처리", description = "카카오 인가코드를 받아 로그인")
    @GetMapping("/login")
    public ResponseEntity<?> kakaoLogin(@RequestParam("code") String code) {
        return ResponseEntity.ok(kakaoService.handleKakaoLogin(code));
    }

    @Operation(summary = "회원가입", description = "회원가입을 진행합니다.")
    @PostMapping("/signup")
    public ResponseEntity<TokenDto> signupOwner(@RequestBody SignupRequest request) {
        TokenDto token = kakaoService.signup(request);
        return ResponseEntity.ok(token);
    }

    @Operation(summary = "JWT 재발급", description = "Refresh Token을 이용해 Access Token 재발급")
    @PostMapping("/refresh")
    public ResponseEntity<TokenDto> refresh(@RequestParam("refreshToken") String refreshToken) {
        return ResponseEntity.ok(kakaoService.refreshToken(refreshToken));
    }





}
