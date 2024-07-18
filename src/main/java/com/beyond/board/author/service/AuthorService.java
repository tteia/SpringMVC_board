package com.beyond.board.author.service;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.dto.AuthorDetailDto;
import com.beyond.board.author.dto.AuthorListResDto;
import com.beyond.board.author.dto.AuthorSaveReqDto;
import com.beyond.board.author.repository.AuthorRepository;
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
        Author author = authorSaveReqDto.toEntity();
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

    public AuthorDetailDto authorDetailDto(Long id){
        Author author = authorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("회원을 찾을 수 없습니다."));
        AuthorDetailDto authorDetailDto = new AuthorDetailDto();
        return authorDetailDto.fromEntity(author);

        // 아래 완성해보기
//        AuthorDetailDto authorDetailDto1 = new authorDetailDto.fromEntity(author);
//        return authorDetailDto1;

    }

    public Author authorFindByEmail(String email){
        Author author = authorRepository.findByEmail(email).orElseThrow(()-> new EntityNotFoundException("해당 Email 을 가진 사용자가 없습니다."));
        return author;
    }
}
