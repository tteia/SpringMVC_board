package com.beyond.board.author.controller;

import com.beyond.board.author.dto.AuthorDetailDto;
import com.beyond.board.author.dto.AuthorListResDto;
import com.beyond.board.author.dto.AuthorSaveReqDto;
import com.beyond.board.author.dto.AuthorUpdateReqDto;
import com.beyond.board.author.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/author")
@Controller
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @GetMapping("/")
    public String home(){
        return "common/home";
    }

    @GetMapping("/register")
    public String memberCreate(){
        return "author/author_register";
    }

    @PostMapping("/register")
    public String authorCreate(@ModelAttribute AuthorSaveReqDto dto){
        authorService.authorCreate(dto);
        return "redirect:/author/list";
    }

    @GetMapping("/list")
    public String authorList(Model model){
        List<AuthorListResDto> authorList = authorService.authorList();
        model.addAttribute("authorList", authorList);
        return "author/author_list";
    }

    @GetMapping("/detail/{id}")
    public String authorDetailList(@PathVariable Long id, Model model){
//        try{
//            AuthorDetailDto authorDetailDto = authorService.authorDetail(id);
//            model.addAttribute("author", authorDetailDto);
//        }
//        catch (IllegalArgumentException e){
//            log.error(id + e.getMessage());
//        }
//        log.info("Get 요청입니다. parameter 는 " + id + ".");
//        log.info("method 명 : authorDetail.");

        AuthorDetailDto authorDetailDto = authorService.authorDetail(id);
        model.addAttribute("author", authorDetailDto);
        return "/author/author_detail";
    }

    @GetMapping("/delete/{id}")
    public String authorDelete(@PathVariable Long id, Model model){
        authorService.authorDelete(id);
        return "redirect:/author/list";
    }

    @GetMapping("/update/{id}")
    public String authorUpdateGet(){
        return "author/author_detail";
    }

    @PostMapping("/update/{id}")
    public String authorUpdatePost(@PathVariable Long id, AuthorUpdateReqDto authorUpdateReqDto){
        authorService.authorUpdate(id, authorUpdateReqDto);
        return "redirect:/author/detail/"+id;
    }

}
