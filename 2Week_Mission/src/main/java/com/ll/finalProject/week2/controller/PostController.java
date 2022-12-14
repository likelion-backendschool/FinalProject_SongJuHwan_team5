package com.ll.finalProject.week2.controller;

import com.ll.finalProject.week2.domain.Member;
import com.ll.finalProject.week2.domain.Post;
import com.ll.finalProject.week2.domain.PostHashTag;
import com.ll.finalProject.week2.domain.PostKeyword;
import com.ll.finalProject.week2.dto.PostDto;
import com.ll.finalProject.week2.service.HashTagService;
import com.ll.finalProject.week2.service.KeywordService;
import com.ll.finalProject.week2.service.MemberService;
import com.ll.finalProject.week2.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final KeywordService keywordService;
    private final HashTagService hashTagService;
    private final MemberService memberService;

    @GetMapping("/list")
    public String postList(Model model){
       List<Post> postList = postService.findAll();
       List<PostHashTag> postHashTagList = hashTagService.findAll();
       model.addAttribute("hashTagList", postHashTagList);
       model.addAttribute("postList", postList);
       return "post/list";
    }

    @GetMapping("/write")
    @PreAuthorize("isAuthenticated()")
    public String writePost(PostDto postDto){
        return "post/write";
    }

    @PostMapping("/write")
    @PreAuthorize("isAuthenticated()")
    public String writePost(@Valid PostDto postDto, BindingResult bindingResult, @RequestParam String keyword){
        if(bindingResult.hasErrors()){
            return "post/write";
        }
        try{
            postService.write(postDto, keyword);
        } catch(Exception e){
            e.printStackTrace();
            bindingResult.reject("writeFailed", e.getMessage());
            return "post/write";
        }
        return "redirect:/post/list";
    }

    @GetMapping("/{postId}")
    @PreAuthorize("isAuthenticated()")
    public String postDetail(@PathVariable Long postId, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Member member = memberService.findByUserName(user.getUsername());
        Post post = postService.findById(postId);
        PostHashTag postHashTag = hashTagService.findByPostId(postId);
        model.addAttribute("member", member);
        model.addAttribute("post", post);
        model.addAttribute("hashTag", postHashTag);
        return "post/detail";
    }

    @GetMapping("/{postId}/delete")
    @PreAuthorize("isAuthenticated()")
    public String deletePost(@PathVariable Long postId){
        Post post = postService.findById(postId);
        PostHashTag postHashTag = hashTagService.findByPostId(postId);
        PostKeyword postKeyword = keywordService.findById(postHashTag.getPostKeyword().getId());
        postService.delete(post, postKeyword, postHashTag);
        return "redirect:/post/list";
    }

    @GetMapping("/{postId}/modify")
    @PreAuthorize("isAuthenticated()")
    public String modifyPost(@PathVariable Long postId, PostDto postDto, Model model){
        Post post = postService.findById(postId);
        postService.saveNewPostDto(post, postDto);
        model.addAttribute("postId", postId);
        model.addAttribute("postDto", postDto);
        return "post/modify";
    }

    @PostMapping("/{postId}/modify")
    @PreAuthorize("isAuthenticated()")
    public String modifyPost(@PathVariable Long postId, @Valid PostDto postDto, BindingResult bindingResult, @RequestParam String keyword){
        Post post = postService.findById(postId);
        PostHashTag postHashTag = hashTagService.findByPostId(postId);
        PostKeyword postKeyword = keywordService.findById(postHashTag.getPostKeyword().getId());
        if(bindingResult.hasErrors()){
            return "post/modify";
        }
        try{
            postService.modify(post, postKeyword, postHashTag, postDto, keyword);
        } catch(Exception e){
            e.printStackTrace();
            bindingResult.reject("modifyFailed", e.getMessage());
            return "post/modify";
        }
        return "redirect:/post/%d".formatted(postId);

    }

}
