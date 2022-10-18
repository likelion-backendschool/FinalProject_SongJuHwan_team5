package com.ll.finalProject.week1.controller;

import com.ll.finalProject.week1.domain.Post;
import com.ll.finalProject.week1.dto.PostDto;
import com.ll.finalProject.week1.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @GetMapping("/list")
    public String postList(Model model){
       List<Post> postList = postService.findAll();
       model.addAttribute("postList", postList);
       return "post/list";
    }

    @GetMapping("/write")
    public String writePost(PostDto postDto){
        return "post/write";
    }

    @PostMapping("/write")
    public String writePost(@Valid PostDto postDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "post/write";
        }
        try{
            postService.write(postDto);
        } catch(Exception e){
            e.printStackTrace();
            bindingResult.reject("writeFailed", e.getMessage());
            return "post/write";
        }
        return "redirect:post/list";
    }
}
