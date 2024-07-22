package com.beyond.board.post.repository;

import com.beyond.board.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
    Page<Post> findAll(Pageable pageable); // post 페이징 처리를 위해 findAll 재정의.
    // Page<Post> = List<Post> + 해당 요소의 Page 정보
    // Pageable : PageNumber(몇 번 페이지인지 - 0번부터 시작됨), Size(페이지마다 몇개씩 들어갈건지 - default:20개.), 정렬 조건

    // JPQL 문법.
    // 네이밍룰을 통한 방식이 아닌 메서드 생성.
    // select p.*, a.* from post p left join author a on p.author_id=a.id;
    @Query("select p from Post p left join fetch p.author")
    List<Post> findAllFetch();

    // fetch 가 아닌 그냥 left join 이라면 ?
    // select p.* from post p left join author a on p.author_id=a.id;
    // => a 를 안 가져 옴 ! -> left join 을 뭐하러 하나요..?
    // author 객체를 통한 조건문으로 post 를 filtering 할 때 사용함. -> 이름이 hong 인 사람의 post 를 가져오겠다!
    // -> N+1 문제가 똑 같 이 발 생.
    @Query("select p from Post p left join p.author")
    List<Post> findAllNOFetch();

    Page<Post> findByAppointment(Pageable pageable, String appointment);
}
