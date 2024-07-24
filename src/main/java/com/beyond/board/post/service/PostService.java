package com.beyond.board.post.service;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.service.AuthorService;
import com.beyond.board.post.domain.Post;
import com.beyond.board.post.dto.PostDetResDto;
import com.beyond.board.post.dto.PostListResDto;
import com.beyond.board.post.dto.PostSaveReqDto;
import com.beyond.board.post.repository.PostRepository;
import com.beyond.board.post.repository.PostUpdateReqDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final AuthorService authorService;

    @Autowired
    public PostService(PostRepository postRepository, AuthorService authorService){
        this.postRepository = postRepository;
        this.authorService = authorService;
    }

    // postService 에서 create 하려고 한다 !
    // Post 객체 : title, contents, author => save => author_id 를 꺼내줌.
    // 왜 객체로 ? getName 등 활용하기 좋음.
    // toEntity 로 조립 중, author 객체를 누가 찾을 것인가 ! => 누굴 AutoWired 할래 ?
    // 우리가 알고 있는 건 email => authorRepository 에서 찾을래 authorService 한테 찾아달라고 할래?
    // 만약 authorRepository 에서 하면 ? 예외 던져주는 것들이 Service 에 있는데 또 해야 됨 ! => authorService 에서 가져오자.
    // => Service 쪽 코드가 고도화, 중복이 심한 경우가 많으므로 authorService 를 autowired.
    // => AuthorService 순환참조 이슈 발생할 수 있음 -> 우리 생성자 방식으로 방지하는 것이 아니라 에러가 발생하는 걸 찾아낼 수 있음.

    public Post postCreate(PostSaveReqDto postSaveReqDto){
//        Author author = authorService.authorFindByEmail(postSaveReqDto.getEmail());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Author author = authorService.authorFindByEmail(username);
        String appointment = null;
        LocalDateTime appointmentTime = null;
        // 만약 Y 이면 !isEmpty() 여야 함. && 비어있어도 null 로 넘어오지는 않으므로 null 체크 안 함.
        if(postSaveReqDto.getAppointment().equals("Y") && !postSaveReqDto.getAppointmentTime().isEmpty()){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            appointmentTime = LocalDateTime.parse(postSaveReqDto.getAppointmentTime(), dateTimeFormatter);
            LocalDateTime now = LocalDateTime.now();
            if(appointmentTime.isBefore(now)){
                throw new IllegalArgumentException("예약 시간은 현재 시간보다 이전일 수 없습니다.");
            }

        }
        Post post = postRepository.save(postSaveReqDto.toEntity(author, appointmentTime));
        return post;
        // authorService 에서 author 객체를 찾아 post의 toEntity 에 넘기고,
        // jpa 가 author 객체에서 author_id 를 찾아 db 에는 author_id 가 들어감.
    }

    public Page<PostListResDto> postList(Pageable pageable){
//        List<Post> posts = postRepository.findAll();
//        List<Post> posts = postRepository.findAllFetch(); // Fetch join 적용 후 코드 수정
//        List<PostListResDto> postListResDtos = new ArrayList<>();
//        for (Post post : posts) { // author 는 post 에 담겨있어요.
//            postListResDtos.add(post.listFromEntity());
//        }

//        Page<Post> posts = postRepository.findAll(pageable);
        Page<Post> posts = postRepository.findByAppointment(pageable, "N");
        Page<PostListResDto> postListResDtos = posts.map(a->a.listFromEntity());
        return postListResDtos;

    }

    //Page 자체가 List 형식이기 때문에 원래는 Page<List<PostListResDto>> 인데 List 를 다 떼줄 수 있음.
    public Page<PostListResDto> postListPage(Pageable pageable){
        Page<Post> posts = postRepository.findAll(pageable);
        Page<PostListResDto> postListResDtos = posts.map(a->a.listFromEntity()); // map : 요소 하나 하나를 순회하며 새 요소를 만드는 것.
        return postListResDtos;
    }

    public PostDetResDto postDetail(Long id){
        Post post = postRepository.findById(id).orElseThrow(()->new EntityNotFoundException("게시글을 찾을 수 없습니다."));
        PostDetResDto postDetResDto = post.detFromEntity();
        return postDetResDto;
    }


    public void postDelete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new EntityNotFoundException("게시글을 찾을 수 없습니다."));
        postRepository.delete(post);
    }

    @Transactional
    public Post postUpdate(Long id, PostUpdateReqDto postUpdateReqDto) {
        Post post = postRepository.findById(id).orElseThrow(()->new EntityNotFoundException("게시글을 찾을 수 없습니다."));
        post.updatePost(postUpdateReqDto);
        Post updateReult = postRepository.save(post);
        return updateReult;
    }
}
