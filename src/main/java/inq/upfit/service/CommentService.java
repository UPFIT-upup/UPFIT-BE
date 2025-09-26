package inq.upfit.service;

import inq.upfit.domain.master.Comment;
import inq.upfit.domain.master.Post;
import inq.upfit.domain.master.User;
import inq.upfit.dto.CommentResponseDto;
import inq.upfit.dto.CommentWriteRequestDto;
import inq.upfit.exception.PostNotFoundException;
import inq.upfit.exception.UserNotFoundException;
import inq.upfit.repository.CommentRepository;
import inq.upfit.repository.PostRepository;
import inq.upfit.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentResponseDto writeComment(Long postId, CommentWriteRequestDto dto, Long userId) {
        // 1. 게시글 존재 확인
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없습니다."));

        // 2. 사용자 확인
        User writer = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        // 3. 댓글 엔티티 생성
        Comment comment = Comment.builder()
                .content(dto.getContent())
                .attachment(dto.getAttachment())
                .writer(writer)
                .post(post)
                .build();

        // 4. 댓글 저장
        Comment savedComment = commentRepository.save(comment);

        log.info("새 댓글이 작성되었습니다 - 댓글 ID: {}, 게시글 ID: {}, 작성자: {}",
                savedComment.getId(), postId, writer.getName());

        return CommentResponseDto.from(savedComment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentsByPostId(Long postId) {
        // 게시글 존재 확인
        postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없습니다."));

        List<Comment> comments = commentRepository.findByPostIdOrderByRegDateAsc(postId);

        return comments.stream()
                .map(CommentResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public long getCommentCount(Long postId) {
        return commentRepository.countByPostId(postId);
    }

}
