package inq.upfit.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Schema(description = "계정이 여러개일때 리스트로 반환")
public class KakaoLoginListResponse {
    private int accountCount;
    private List<KakaoLoginResponse> accounts;
}
