package com.beyond.board.author.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.beyond.board.author.domain.Author;

import java.util.List;
import java.util.Optional;


@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    // findBy컬럼명의 규칙으로 자동으로 where 조건문을 사용한 메서드 쿼리 생성됨.
    // 그 외 : findByNameAndEmail, findByNameOrEmail
    // findByAgeBetween(int start, int end)
    // findByAgeLessThan(int age), findByAgeGreaterThan(int age), findByAgeIsNull, findByAgeIsNotNull
    // List<Author> findAllOrderByEmail();
    Optional<Author> findByEmail(String email);
//    List<Author> findByNameIsNull(); >> 이런 식으로 선언만 해두면 Service 에서 가져다 쓸 수 있다.
}