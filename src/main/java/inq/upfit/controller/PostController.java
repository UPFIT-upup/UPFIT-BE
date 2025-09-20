package inq.upfit.controller;

import inq.upfit.auth.utils.UserDetailsImpl;
import inq.upfit.dto.PostPagedResponseDto;
import inq.upfit.dto.PostResponseDto;
import inq.upfit.dto.PostWriteRequestDto;
import inq.upfit.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> writePost(
            @Valid @RequestBody PostWriteRequestDto dto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long userId = userDetails.getUser().getId();
        PostResponseDto newPostDto = postService.writePost(dto, userId);
        return new ResponseEntity<>(newPostDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 서비스로 삭제 요청
        postService.deletePost(postId, userDetails);

        // 삭제 성공 시 204 No Content 반환
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long postId) {
        PostResponseDto response = postService.getPost(postId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PostPagedResponseDto> getPosts(
            @RequestParam(defaultValue = "ALL") String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "regDate,desc") String sort
    ) {
        PostPagedResponseDto response = postService.getPosts(category, sort, page, size);
        return ResponseEntity.ok(response);
    }

}