package com.beyond.board.author;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.domain.Role;
import com.beyond.board.author.dto.AuthorDetailDto;
import com.beyond.board.author.dto.AuthorSaveReqDto;
import com.beyond.board.author.dto.AuthorUpdateReqDto;
import com.beyond.board.author.repository.AuthorRepository;
import com.beyond.board.author.service.AuthorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;

@SpringBootTest
@Transactional
public class AuthorServiceTest {

    @Autowired
    private AuthorService authorService;

    // 저장 및 detail 조회
    @Test
    void saveAndFind(){
        AuthorSaveReqDto authorDto = new AuthorSaveReqDto(
                "하미니", "test@daum.net",
                "12341234", Role.USER);
        Author author = authorService.authorCreate(authorDto);
        // findByEmail 추가
        Author findEmailAuthor = authorService.authorFindByEmail(authorDto.getEmail());
        // 위 한 줄만 추가해줘도 함께 검증이 가능하다.
        Assertions.assertEquals(authorDto.getEmail(), author.getEmail());
    }

    // update 검증
    // 객체 create
    @Test
    void updateTest(){
        AuthorSaveReqDto authorDto = new AuthorSaveReqDto(
                "하미니", "hamin@test.com",
                "12341234", Role.USER);
        Author author = authorService.authorCreate(authorDto);
        // 수정 작업 => name, password 변경.
        AuthorUpdateReqDto authorUpdateReqDto = AuthorUpdateReqDto.builder()
                .name("아영이")
                .password("43214321")
                .build();

        authorService.authorUpdate(author.getId(), authorUpdateReqDto);

        // 수정 후 재조회 => 바꾼 name, password 각각 검증.
        Author savedAuthor = authorService.authorFindByEmail("hamin@test.com");

        Assertions.assertEquals(authorUpdateReqDto.getName(), savedAuthor.getName());
        Assertions.assertEquals(authorUpdateReqDto.getPassword(), savedAuthor.getPassword());
    }


    // findAll 검증
}
