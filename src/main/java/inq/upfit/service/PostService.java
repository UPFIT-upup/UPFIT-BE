package inq.upfit.service;

import inq.upfit.auth.utils.UserDetailsImpl;
import inq.upfit.domain.master.Post;
import inq.upfit.domain.master.Department;
import inq.upfit.domain.master.User;
import inq.upfit.dto.PostResponseDto;
import inq.upfit.dto.PostWriteRequestDto;
import inq.upfit.exception.PostNotFoundException;
import inq.upfit.exception.UnauthorizedException;
import inq.upfit.exception.UserNotFoundException;
import inq.upfit.repository.DepartmentRepository;
import inq.upfit.repository.PostRepository;
import inq.upfit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;


    @Transactional
    public PostResponseDto writePost(PostWriteRequestDto dto, Long userId) {
        User writer = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("작성자를 찾을 수 없습니다."));

        /*
        잠시 부서 테스트용 나중에 주석 해제해야 함. 밑에 부서 객체 생성 지워야 함.
        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new DepartmentNotFoundException("부서를 찾을 수 없습니다.")); */
        Department department = new Department(1L, "마케팅팀", null);

        Post post = dto.toEntity(writer, department);

        Post savedPost = postRepository.save(post);

        return PostResponseDto.from(savedPost);
    }

    @Transactional
    public void deletePost(Long postId, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없습니다."));

        User currentUser = userDetails.getUser();
        boolean isAdmin = currentUser.isAdmin();

        if (!isAdmin && !post.getWriter().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("삭제 권한이 없습니다.");
        }

        postRepository.delete(post);
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없습니다."));

        return PostResponseDto.from(post); // DTO 변환
    }
}
