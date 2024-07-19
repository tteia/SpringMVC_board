package com.beyond.board.post.controller;

import com.beyond.board.post.dto.PostDetResDto;
import com.beyond.board.post.dto.PostListResDto;
import com.beyond.board.post.dto.PostSaveReqDto;
import com.beyond.board.post.repository.PostUpdateReqDto;
import com.beyond.board.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String postList(Model model){
        model.addAttribute("postList", postService.postList());
        return "/post/post_list";
    }

    @GetMapping("/detail/{id}")
    public String postDetail(@PathVariable Long id, Model model){
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
