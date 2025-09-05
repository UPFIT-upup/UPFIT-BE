package inq.upfit.dto;

import inq.upfit.domain.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;


@Data
@Getter
public class SignupRequest {

    @Schema(description = "카카오 이메일", example = "test@kakao.com")
    private String kakaoEmail;

    @Schema(description = "이름", example = "홍길동")
    private String name;

    @Schema(description = "역할", example ="Admin")
    private Role role;


}
