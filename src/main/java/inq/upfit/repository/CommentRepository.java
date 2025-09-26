package inq.upfit.repository;

import inq.upfit.domain.master.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 게시글의 모든 댓글 조회 (작성일 순)
    List<Comment> findByPostIdOrderByRegDateAsc(Long postId);

    // 특정 게시글의 댓글 개수
    long countByPostId(Long postId);

    // 특정 사용자가 작성한 댓글 조회
    List<Comment> findByWriterIdOrderByRegDateDesc(Long writerId);
}
