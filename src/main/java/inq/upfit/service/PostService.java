package inq.upfit.service;

import inq.upfit.domain.master.Post;
import inq.upfit.domain.master.Department;
import inq.upfit.domain.master.User;
import inq.upfit.dto.PostResponseDto;
import inq.upfit.dto.PostWriteRequestDto;
import inq.upfit.exception.DepartmentNotFoundException;
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

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new DepartmentNotFoundException("부서를 찾을 수 없습니다."));

        Post post = dto.toEntity(writer, department);

        Post savedPost = postRepository.save(post);

        return PostResponseDto.builder()
                .id(savedPost.getId())
                .title(savedPost.getTitle())
                .content(savedPost.getContent())
                .category(savedPost.getCategory())
                .writerName(writer.getName())
                .departmentName(department.getDepartmentName())
                .hit(savedPost.getHit())
                .regDate(savedPost.getRegDate())
                .build();
    }
}
