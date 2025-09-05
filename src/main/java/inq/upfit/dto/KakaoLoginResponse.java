package inq.upfit.dto;


import inq.upfit.domain.Role;
import inq.upfit.domain.master.Company;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "카카오 로그인 응답 DTO")
public class KakaoLoginResponse {

    @Schema(description = "가입 여부", example = "true")
    private Boolean isSignedUp;

    @Schema(description = "카카오 이메일", example = "@~")
    private String kakaoEmail;

    @Schema(description = "JWT 토큰 정보(가입된 사용자만 소유)")
    private TokenDto tokenDto;

    @Schema(description = "관리자 여부")
    private Role role;




}
