package inq.upfit.dto;


import inq.upfit.domain.Company;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "카카오 로그인 응답 DTO")
public class KakaoLoginResponse {

    @Schema(description = "가입 여부", example = "true")
    private Boolean isSignedUp;

    @Schema(description = "카카오 id", example = "123ddfnaiew")
    private String kakaoId;

    @Schema(description = "JWT 토큰 정보(가입된 사용자만 소유)")
    private TokenDto tokenDto;

    //계정과 함께 소속된 회사도 리턴하여 프론트에 전달
    @Schema(description = "회사명")
    private Company company;



}
