package com.ll.finalProject.week1.controller;

import com.ll.finalProject.week1.domain.Member;
import com.ll.finalProject.week1.domain.Post;
import com.ll.finalProject.week1.domain.PostHashTag;
import com.ll.finalProject.week1.domain.PostKeyword;
import com.ll.finalProject.week1.dto.PostDto;
import com.ll.finalProject.week1.repository.HashTagRepository;
import com.ll.finalProject.week1.repository.KeywordRepository;
import com.ll.finalProject.week1.service.HashTagService;
import com.ll.finalProject.week1.service.KeywordService;
import com.ll.finalProject.week1.service.MemberService;
import com.ll.finalProject.week1.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
    public String writePost(PostDto postDto){
        return "post/write";
    }

    @PostMapping("/write")
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
    public String deletePost(@PathVariable Long postId){
        Post post = postService.findById(postId);
        PostHashTag postHashTag = hashTagService.findByPostId(postId);
        PostKeyword postKeyword = keywordService.findById(postHashTag.getPostKeyword().getId());
        postService.delete(post, postKeyword, postHashTag);
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
