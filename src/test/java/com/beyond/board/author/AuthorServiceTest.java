package com.beyond.board.author;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.domain.Role;
import com.beyond.board.author.dto.AuthorDetailDto;
import com.beyond.board.author.dto.AuthorListResDto;
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

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class AuthorServiceTest {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorRepository authorRepository;

    // 저장 및 detail 조회
    @Test
    void saveAndFind(){
        AuthorSaveReqDto authorDto = new AuthorSaveReqDto(
                "하미니", "test@daum.net",
                "12341234", Role.USER);
        Author author = authorService.authorCreate(authorDto);
        //findById 추가 => Mock 에서 임시 Repository 생성하기 전에 authorRepository 를 이용하는 코드로 수정하기 !
        Author authorDetail = authorRepository.findById(author.getId()).orElseThrow(()->new EntityNotFoundException("존재하지 않는 회원입니다."));
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
        Author savedAuthor = authorRepository.findById(author.getId()).orElseThrow(()->new EntityNotFoundException("존재하지 않는 회원입니다."));

        Assertions.assertEquals(authorUpdateReqDto.getName(), savedAuthor.getName());
        Assertions.assertEquals(authorUpdateReqDto.getPassword(), savedAuthor.getPassword());
    }


    // findAll 검증
    @Test
    public void findAllTest(){
        // 저장하기 전 List
        List<AuthorListResDto> List1 = authorService.authorList();

        // Author 3명 저장 - authorCreate
        AuthorSaveReqDto authorDto1 = new AuthorSaveReqDto(
                "클로버", "clover@test.com",
                "12341234", Role.USER);
        AuthorSaveReqDto authorDto2 = new AuthorSaveReqDto(
                "체리", "cherry@test.com",
                "12341234", Role.USER);
        AuthorSaveReqDto authorDto3 = new AuthorSaveReqDto(
                "토끼", "tokki@test.com",
                "12341234", Role.USER);
        Author clover = authorService.authorCreate(authorDto1);
        Author cherry = authorService.authorCreate(authorDto2);
        Author tokki = authorService.authorCreate(authorDto3);

        // authorList 를 조회한 후 저장한 개수와 저장된 개수 비교.
        List<AuthorListResDto> List2 = authorService.authorList();
        Assertions.assertEquals(List1.size() + 3, List2.size()); // 기존 DB 에 저장된 값이 있으므로 저장한 3명 더해줘서 비교.
    }


}
