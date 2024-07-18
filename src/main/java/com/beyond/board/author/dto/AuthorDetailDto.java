package com.beyond.board.author.dto;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDetailDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime createdTime;
    private Role role;
    private int postCounts;

    public AuthorDetailDto fromEntity(Author author){
        AuthorDetailDto authorDetailDto = AuthorDetailDto.builder()
                .id(author.getId())
                .name(author.getName()).email(author.getEmail())
                .password(author.getPassword()).createdTime(author.getCreatedTime())
                .role(author.getRole()).postCounts(author.getPosts().size())
                .build();
        return authorDetailDto;
    }
}
