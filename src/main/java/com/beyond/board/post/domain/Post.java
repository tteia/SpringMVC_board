package com.beyond.board.post.domain;

import com.beyond.board.author.domain.Author;
import com.beyond.board.post.dto.PostDetResDto;
import com.beyond.board.post.dto.PostListResDto;
import com.beyond.board.post.repository.PostUpdateReqDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 3000)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @CreationTimestamp
    private LocalDateTime createdTime;
    @UpdateTimestamp
    private LocalDateTime updateTime;

    public PostListResDto listFromEntity() {
        return PostListResDto.builder()
                .id(this.id)
                .title(this.title)
                .author_email(author.getEmail())
                .build();
    }

    public PostDetResDto detFromEntity(){
        return PostDetResDto.builder()
                .id(this.id)
                .title(this.title)
                .contents(this.contents)
                .author_email(this.author.getEmail())
                .createdTime(this.getCreatedTime())
                .updatedTime(this.getUpdateTime())
                .build();
    }

    public void updatePost(PostUpdateReqDto postUpdateReqDto) {
        this.title = postUpdateReqDto.getTitle();
        this.contents = postUpdateReqDto.getContents();
    }
}
