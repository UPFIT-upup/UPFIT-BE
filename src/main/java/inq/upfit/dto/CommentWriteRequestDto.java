package inq.upfit.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentWriteRequestDto {
    @NotBlank(message = "댓글 내용은 필수 입력 값입니다.")
    private String content;
    private String attachment;

}
