package inq.upfit.controller;

import inq.upfit.auth.utils.UserDetailsImpl;
import inq.upfit.dto.CommentResponseDto;
import inq.upfit.dto.CommentWriteRequestDto;
import inq.upfit.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> writeComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentWriteRequestDto dto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long userId = userDetails.getUser().getId();
        CommentResponseDto response = commentService.writeComment(postId, dto, userId);

        log.info("댓글 작성 완료 - 게시글 ID: {}, 댓글 ID: {}", postId, response.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long postId) {
        List<CommentResponseDto> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCommentCount(@PathVariable Long postId) {
        long count = commentService.getCommentCount(postId);
        return ResponseEntity.ok(count);
    }
}
