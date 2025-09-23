package inq.upfit.dto;

import inq.upfit.domain.PostCategory;
import inq.upfit.domain.master.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String attachment;
    private PostCategory category;
    private String writerName;
    private String departmentName;
    private int hit;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    public static PostResponseDto from(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .attachment(post.getAttachment())
                .category(post.getCategory())
                .writerName(post.getWriter().getName())
                .departmentName(post.getDepartment().getDepartmentName())
                .hit(post.getHit())
                .regDate(post.getRegDate())
                .modDate(post.getModDate())
                .build();
    }
}