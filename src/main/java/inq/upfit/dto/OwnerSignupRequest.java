package inq.upfit.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "대표 전용 회원가입 DTO")
public class OwnerSignupRequest {

    @Schema(description = "이름")
    private String name;

    @Schema(description = "카카오 id")
    private String kakaoId;

    @Schema(description ="회사 고유넘버", example = "UUID  문자열" )
    private String companyCode;

    @Schema(description = "회사명", example = "Inq")
    private String company;

    @Schema(description = "사용할 이메일 주소", example = "123@kyonggi.ac.kr")
    private String email;

    @Schema(description = "전화번호", example = "010-4885-4885")
    private String tel_no;


}
