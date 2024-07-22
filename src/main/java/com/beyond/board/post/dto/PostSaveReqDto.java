package com.beyond.board.post.dto;

import com.beyond.board.author.domain.Author;
import com.beyond.board.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostSaveReqDto {
    private String title;
    private String email; // 원래는 로그인 ! 우리는 이메일 입력 받는 형식으로 해보는 것. (추후 변경)
    private String contents;
    private String appointment;
    private String appointmentTime;

    public Post toEntity(Author author, LocalDateTime appointmentTime){
        return Post.builder()
                .title(this.title)
                .contents(this.contents)
                .appointment(this.appointment)
                .appointmentTime(appointmentTime)
                .author(author)
                .build();
    }
}
