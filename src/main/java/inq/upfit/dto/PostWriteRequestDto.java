package inq.upfit.dto;


import inq.upfit.domain.PostCategory;
import inq.upfit.domain.master.Department;
import inq.upfit.domain.master.Post;
import inq.upfit.domain.master.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostWriteRequestDto {
    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String content;

    private String attachment;

    @NotNull(message = "카테고리는 필수 입력 값입니다.")
    @Enumerated(EnumType.STRING)
    private PostCategory category;

    @NotNull(message = "부서 ID는 필수 선택 값입니다.")
    private Long departmentId;

    public Post toEntity(User writer, Department department) {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .attachment(this.attachment)
                .category(this.category)
                .writer(writer)
                .department(department)
                .hit(0)
                .build();
    }
}