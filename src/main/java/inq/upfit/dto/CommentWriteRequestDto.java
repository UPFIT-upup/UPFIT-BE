package inq.upfit.dto;

import inq.upfit.domain.master.Comment;
import inq.upfit.domain.master.Post;
import inq.upfit.domain.master.User;
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

    // 엔티티 변환 메소드 (선택사항)
    public Comment toEntity(User writer, Post post) {
        return Comment.builder()
                .content(this.content)
                .attachment(this.attachment)
                .writer(writer)
                .post(post)
                .build();
    }

}
