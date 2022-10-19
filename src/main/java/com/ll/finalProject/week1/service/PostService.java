package com.ll.finalProject.week1.service;

import com.ll.finalProject.week1.domain.Member;
import com.ll.finalProject.week1.domain.Post;
import com.ll.finalProject.week1.dto.PostDto;
import com.ll.finalProject.week1.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberService memberService;

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public void write(PostDto postDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Member member = memberService.findByUserName(user.getUsername());

        Post post = new Post();
        post.setMember(member);
        post.setSubject(postDto.getSubject());
        post.setContent(postDto.getContent());
        post.setContentHTML(postDto.getContentHTML());

        postRepository.save(post);
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId).orElseThrow(null);
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }

    public void saveNewPostDto(Post post, PostDto postDto) {
        postDto.setSubject(post.getSubject());
        postDto.setContent(post.getContent());
        postDto.setContentHTML(post.getContentHTML());
    }

    public void modify(Post post , PostDto postDto) {
        post.setSubject(postDto.getSubject());
        post.setContent(postDto.getContent());
        post.setContentHTML(postDto.getContentHTML());
        postRepository.save(post);
    }
}
