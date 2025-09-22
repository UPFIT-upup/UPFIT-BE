package inq.upfit.domain.master;

import com.fasterxml.jackson.annotation.JsonIgnore;
import inq.upfit.domain.PostCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long id;

    @NotNull
    @Column(name = "TITLE", nullable = false, length = 255)
    private String title;

    @NotNull
    @Column(name = "CONTENT", nullable = false, length = 1000)
    private String content;

    @Column(name = "ATTACHMENT", length = 1000)
    private String attachment;

    @NotNull
    @Column(name = "HIT", nullable = false)
    private int hit=0;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY", nullable = false)
    private PostCategory category;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WRITER_ID", nullable = false)
    private User writer;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPT_ID", nullable = false)
    private Department department;

    // (추가) 등록일
    @CreatedDate
    @Column(name = "REGDATE", updatable = false)
    private LocalDateTime regDate;

    
    //변경일
    @LastModifiedDate
    @Column(name = "MODDATE")
    private LocalDateTime modDate;

    public void update(String title, String content, String attachment, PostCategory category, Department department) {
        this.title = title;
        this.content = content;
        this.attachment = attachment;
        this.category = category;
        this.department = department;
    }

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // 무한 루프 방지
    private List<Comment> comments;
}
