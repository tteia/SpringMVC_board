package com.beyond.board.author;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.domain.Role;
import com.beyond.board.author.dto.AuthorDetailDto;
import com.beyond.board.author.dto.AuthorSaveReqDto;
import com.beyond.board.author.repository.AuthorRepository;
import com.beyond.board.author.service.AuthorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@SpringBootTest
@Transactional
public class AuthorServiceMockTest {
    @Autowired
    private AuthorService authorService;

    // 진짜 Repository
//    @Autowired
//    private AuthorRepository authorRepository;

    // 임시 Repository 객체 만들기
    @MockBean
    private AuthorRepository authorRepository;

    @Test
    void findAuthorDetailTest(){
//        AuthorDetailDto authorDetail = authorService.authorDetail();
        // 위 코드를 검증해보려면 DB 의 Id 값을 비교해야 함 -> 우리는 로직만 검증하려고 하는 것 !
        // => 임시 DB 를 만들어서 비교해보면 됨 (가짜 Repository 를 만들자 !)=> 그것이 Mocking

        AuthorSaveReqDto author = new AuthorSaveReqDto(
                "토끼", "tokki@test.com",
                "12341234", Role.USER);
        Author author1 = authorService.authorCreate(author);
        AuthorDetailDto authorDetailDto = authorService.authorDetail(author1.getId());
//        Author savedAuthor = authorRepository.findById(author1.getId()).orElseThrow(()->new EntityNotFoundException("존재하지 않는 회원입니다."));
        Mockito.when(authorRepository.findById(author1.getId())).thenReturn(Optional.of(author1));
        Author savedAuthor = authorRepository.findById(author1.getId()).orElseThrow(()->new EntityNotFoundException("존재하지 않는 회원입니다."));
        Assertions.assertEquals(authorDetailDto.getEmail(), author1.getEmail());

        // service 를 테스트 할거야.
        // repository 변수가 발생할 여지가 있다 -> 왜냐 ! 실제 DB 는 계속 변화하고 있을 수 있으니까. (지금 우리는 괜찮지만 만약 방대한 실제라면?)
        // => 가짜 Repository 객체를 만들어서 일관된 응답을 받을 수 있도록 하자 !
        // => @MockBean : 임시(가짜) 객체 정의.
        // => Mochito.when(만약에 when 이면).then(직접 정의한 객체를 리턴해)
        // 우리는 AuthorSaveReqDto 를 만들어서 비교했지만, Author 를 정의해서 활용해주는 것이 더 적절하다. (Author mockAuthor = Author.builder().build())
        // .findById(author1.getId())) 보다 .findById(1L)) 이 더 정확하다. 전자는 DB 에 들어가는 것에 따라 값이 변화하니까.
    }
}
