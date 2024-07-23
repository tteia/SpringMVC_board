package com.beyond.board.author.service;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.dto.AuthorDetailDto;
import com.beyond.board.author.dto.AuthorListResDto;
import com.beyond.board.author.dto.AuthorSaveReqDto;
import com.beyond.board.author.dto.AuthorUpdateReqDto;
import com.beyond.board.author.repository.AuthorRepository;
import com.beyond.board.post.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true) // 조회 작업 시 ReadOnly > 성능 향상. 다만 저장 작업 시에는 메서드 위에 별도로 Transactional 작성.
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    // 정보가 있기 때문에 Author 를 사용자에게 바로 쏘진 않을 것.
    @Transactional
    public Author authorCreate(AuthorSaveReqDto authorSaveReqDto){
        if(authorRepository.findByEmail(authorSaveReqDto.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 Email 입니다.");
        }
        if(authorSaveReqDto.getPassword().length() < 8){
            throw new IllegalArgumentException("비밀번호가 너무 짧습니다. (8자 이상 설정)");
        }
        Author author = authorSaveReqDto.toEntity();
        //Cascade persist test. (remove 테스트는 회원 삭제로 대체 !)
        // .getPosts() 때문에 nullPointer 에러 발생함. (지금 가입하니까 post 작성 기록이 없잔아)
        author.getPosts().add(Post.builder()
                .title("환영합니다 !")
                .author(author)
                .contents("안녕하세요, "+authorSaveReqDto.getName() + "님 ! 🫐").build());
        Author savedAuthor = authorRepository.save(author);
        return savedAuthor;
    }

    public List<AuthorListResDto> authorList(){
        List<AuthorListResDto> authorListResDtos = new ArrayList<>();
        List<Author> authorList = authorRepository.findAll();
        for (Author author : authorList) {
            AuthorListResDto authorListResDto = author.fromEntity();
            authorListResDtos.add(authorListResDto);
        }
        return authorListResDtos;
    }

    public AuthorDetailDto authorDetail(Long id){
        Author author = authorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("회원을 찾을 수 없습니다."));
        AuthorDetailDto authorDetailDto = new AuthorDetailDto();
        return authorDetailDto.fromEntity(author);

        // 아래 완성해보기
//        AuthorDetailDto authorDetailDto1 = new authorDetail.fromEntity(author);
//        return authorDetailDto1;

    }

    public Author authorFindByEmail(String email){
        Author author = authorRepository.findByEmail(email).orElseThrow(()-> new EntityNotFoundException("해당 Email 을 가진 사용자가 없습니다."));
        return author;
    }

    @Transactional
    public void authorDelete(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("회원을 찾을 수 없습니다."));
        authorRepository.delete(author);
    }

    @Transactional
    public void authorUpdate(Long id, AuthorUpdateReqDto authorUpdateReqDto){
        Author author = authorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("회원을 찾을 수 없습니다."));
        author.updateAuthor(authorUpdateReqDto);
        //        authorUpdateReqDto.toEntity(author);

        // jpa 가 특정 엔티티의 변경을 자동으로 인지하고 변경사항을 DB 에 반영하는 것이 dirty checking
        // 회원 가입은 "추가" . 변경이 아님 ! => 더티 체킹 불가.
        // readOnly 가 아니어도 더티체킹 시 @Transactional 꼭 붙어있어야 함. (maybe...?) // 나 readOnly 잘 모름 ㅠ 어노테이션 책 읽고 싶다..
        // 기존 코드를 주석처리하고 test. 리턴 타입도 void 로 변경 (기존 Author)
//        Author updatedResult = authorRepository.save(author);
//        return updatedResult;
    }
}
