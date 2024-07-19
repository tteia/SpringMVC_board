package com.beyond.board.author.domain;

import com.beyond.board.author.dto.AuthorDetailDto;
import com.beyond.board.author.dto.AuthorListResDto;
import com.beyond.board.author.dto.AuthorUpdateReqDto;
import com.beyond.board.common.BaseTimeEntity;
import com.beyond.board.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder // Builder 어노테이션을 통해 매개변수의 순서, 개수 등을 유연하게 설정 가능. => 생성자로서 활용.
@AllArgsConstructor
@NoArgsConstructor
public class Author extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20, nullable = false)
    private String name;
    @Column(length = 30, nullable = false, unique = true)
    private String email;
    @Column(nullable = false) // 안 쓰면 길이 제한 255
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    // 일반적으로 부모 엔티티에 cascade 옵션을 설정한다. (자식 객체에 영향을 줄 수 있는 Entity)
//    @OneToMany(mappedBy = "author")
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Post> posts;

    public AuthorListResDto fromEntity(){
        AuthorListResDto authorListResDto = AuthorListResDto.builder()
                .id(this.id).name(this.name)
                .email(this.email)
                .build();
        return authorListResDto;
    }

    public void updateAuthor(AuthorUpdateReqDto authorUpdateReqDto) {
        this.name = authorUpdateReqDto.getName();
        this.password = authorUpdateReqDto.getPassword();
    }


//    @Builder
//    public Author(String name, String email, String password, Role role){
//        this.name = name;
//        this.email = email;
//        this.password = password;
//        this.role = role;
//    }

}
