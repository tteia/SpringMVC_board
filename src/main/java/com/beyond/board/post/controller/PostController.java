package com.beyond.board.post.controller;

import com.beyond.board.post.dto.PostDetResDto;
import com.beyond.board.post.dto.PostListResDto;
import com.beyond.board.post.dto.PostSaveReqDto;
import com.beyond.board.post.repository.PostUpdateReqDto;
import com.beyond.board.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/post")
@Controller
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }
    @GetMapping("/create")
    public String postCreateGet(PostSaveReqDto postSaveReqDto){
        return "/post/post_register";
    }

    @PostMapping("/create")
    public String postCreate(PostSaveReqDto postSaveReqDto){
        postService.postCreate(postSaveReqDto);
        return "redirect:/post/list";
    }

    @GetMapping("/list")
    public String postList(Model model, @PageableDefault(size = 10, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable){
        model.addAttribute("postList", postService.postList(pageable));
        return "/post/post_list";
    }

    // 페이징 처리 -> 서버 부하를 줄여주기 위해 처리함.
    // 1-6 까지의 페이지가 있다면 ? 각 페이지를 누를 때마다 그 페이지의 값을 요청해서 불러서 가져옴

    @GetMapping("/list/page")
    @ResponseBody
    // Pageable 요청 방법 : localhost:8080/post/list/page?size=10&page=0
    public Page<List<PostListResDto>> postListTest(@PageableDefault(size = 10, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable){ // 디폴트로 지정한 건 사용자가 주소창에서 수정하면 덮어씌워짐 !
        postService.postListPage(pageable);
        return null;
    }

    @GetMapping("/detail/{id}")
    public String postDetail(@PathVariable Long id, Model model){
//        log.info("Get 요청입니다. parameter 는 " + id + ".");
//        log.info("method 명 : postDetail.");
        model.addAttribute("post", postService.postDetail(id));
        return "/post/post_detail";
    }

    @GetMapping("/delete/{id}")
    public String postDelete(@PathVariable Long id){
        postService.postDelete(id);
        return "redirect:/post/list";
    }

    @GetMapping("/update/{id}")
    public String postUpdateGet(){
        return "post/post_detail";
    }

    @PostMapping("/update/{id}")
    public String postUpdatePost(@PathVariable Long id, PostUpdateReqDto postUpdateReqDto){
        postService.postUpdate(id, postUpdateReqDto);
        return "redirect:/post/detail/"+id;
    }

}
