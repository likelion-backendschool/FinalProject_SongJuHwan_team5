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
import org.springframework.web.bind.annotation.PathVariable;
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
        return "redirect:/post/list";
    }

    @GetMapping("/{postId}")
    public String postDetail(@PathVariable Long postId, Model model){
        Post post = postService.findById(postId);
        model.addAttribute("post", post);
        return "post/detail";
    }

    @GetMapping("/{postId}/delete")
    public String deletePost(@PathVariable Long postId){
        Post post = postService.findById(postId);
        postService.delete(post);
        return "redirect:/post/list";
    }

    @GetMapping("/{postId}/modify")
    public String modifyPost(@PathVariable Long postId, PostDto postDto, Model model){
        Post post = postService.findById(postId);
        postService.saveNewPostDto(post, postDto);
        model.addAttribute("postId", postId);
        model.addAttribute("postDto", postDto);
        return "post/modify";
    }

    @PostMapping("/{postId}/modify")
    public String modifyPost(@PathVariable Long postId, @Valid PostDto postDto, BindingResult bindingResult){
        Post post = postService.findById(postId);
        if(bindingResult.hasErrors()){
            return "post/modify";
        }
        try{
            postService.modify(post, postDto);
        } catch(Exception e){
            e.printStackTrace();
            bindingResult.reject("modifyFailed", e.getMessage());
            return "post/modify";
        }
        return "redirect:/post/%d".formatted(postId);

    }

}
