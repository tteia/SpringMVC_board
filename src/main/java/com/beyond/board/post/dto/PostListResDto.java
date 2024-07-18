package com.beyond.board.post.dto;

import com.beyond.board.author.domain.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostListResDto {
    private Long id;
    private String title;
    // Author 객체 그 자체 ({id:1, name:tteia...}) 를 return 하게 되면 Author 안에 Post 가 재참조 되어 순환참조 이슈 발생.
    private String author_email;
}
