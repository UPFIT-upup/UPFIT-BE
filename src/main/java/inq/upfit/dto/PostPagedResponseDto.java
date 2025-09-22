package inq.upfit.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PostPagedResponseDto {
    private List<PostResponseDto> posts;  // 게시글 목록
    private int page;                     // 현재 페이지 번호
    private int size;                     // 페이지 크기
    private long totalElements;           // 전체 게시글 수
    private int totalPages;               // 전체 페이지 수
    private boolean last;                 // 마지막 페이지 여부
    private String sortField;             // 정렬 필드
    private String sortDirection;         // 정렬 방향 (ASC, DESC)
    private String category;
}
