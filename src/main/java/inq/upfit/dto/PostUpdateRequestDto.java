package inq.upfit.dto;

import inq.upfit.domain.PostCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostUpdateRequestDto {
    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String content;

    private String attachment;

    @NotNull(message = "카테고리는 필수 입력 값입니다.")
    private PostCategory category;

    @NotNull(message = "부서 ID는 필수 선택 값입니다.")
    private Long departmentId;
}
