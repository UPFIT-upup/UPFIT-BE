package inq.upfit.auth.kakao;

import inq.upfit.dto.KakaoLoginResponse;
import inq.upfit.dto.MemberSignupRequest;
import inq.upfit.dto.OwnerSignupRequest;
import inq.upfit.dto.TokenDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "KakaoController", description = "카카오 로그인 및 회원가입 API")
public class KakaoController {

    private final KaKaoService kakaoService;

    @Operation(summary = "카카오 로그인 및 소셜 회원가입 처리", description = "카카오 인가코드를 받아 로그인 또는 계정 선택(소셜 회원가입 클릭 시에도 해당 엔드포인트)")
    @GetMapping("/login")
    public ResponseEntity<?> kakaoLogin(@RequestParam("code") String code) {
        return ResponseEntity.ok(kakaoService.handleKakaoLogin(code));
    }

    @Operation(summary = "대표 회원가입", description = "소셜 회원가입 (카카오 로그인 흐름)이후 회사 대표 회원가입 정보를 입력하고 회사 대표가 회원가입을 진행합니다.")
    @PostMapping("/signup/owner")
    public ResponseEntity<TokenDto> signupOwner(@RequestBody OwnerSignupRequest request) {
        TokenDto token = kakaoService.signupOwner(request);
        return ResponseEntity.ok(token);
    }

    @Operation(summary = "일반 멤버 회원가입", description = "소셜 회원가입( 카카오 로그인 흐름) 이후 멤버가 정보를 입력하고 회사에 소속된 일반 멤버가 회원가입을 진행합니다.")
    @PostMapping("/signup/member")
    public ResponseEntity<TokenDto> signupMember(@RequestBody MemberSignupRequest request) {
        TokenDto token = kakaoService.signupMember(request);
        return ResponseEntity.ok(token);
    }





}
