package inq.upfit.dto;

import inq.upfit.domain.master.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentResponseDto {
    private Long id;
    private String content;
    private String attachment;
    private String writerName;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    public static CommentResponseDto from(Comment comment) {
        return  CommentResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .attachment(comment.getAttachment())
                .writerName(comment.getWriter().getName())
                .regDate(comment.getRegDate())
                .modDate(comment.getModDate())
                .build();

    }
}
