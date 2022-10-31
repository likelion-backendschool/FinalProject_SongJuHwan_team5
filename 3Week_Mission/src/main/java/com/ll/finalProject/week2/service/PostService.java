package com.ll.finalProject.week2.service;

import com.ll.finalProject.week2.domain.Member;
import com.ll.finalProject.week2.domain.Post;
import com.ll.finalProject.week2.domain.PostHashTag;
import com.ll.finalProject.week2.domain.PostKeyword;
import com.ll.finalProject.week2.dto.PostDto;
import com.ll.finalProject.week2.repository.HashTagRepository;
import com.ll.finalProject.week2.repository.KeywordRepository;
import com.ll.finalProject.week2.repository.PostRepository;
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
    private final KeywordRepository keywordRepository;
    private final HashTagRepository hashTagRepository;

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public void write(PostDto postDto, String keyword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Member member = memberService.findByUserName(user.getUsername());
        String[] value = keyword.split(",");
        StringBuilder sb = new StringBuilder();

        Post post = new Post();
        post.setMember(member);
        post.setSubject(postDto.getSubject());
        post.setContent(postDto.getContent());
        post.setContentHTML(postDto.getContentHTML());
        postRepository.save(post);

        PostKeyword postKeyword = new PostKeyword();
        for(int i = 0 ; i < value.length; i++){
            if(i == value.length-1){
                sb.append("#");
                sb.append(value[i].split("\"")[3]);
            } else {
                sb.append("#");
                sb.append(value[i].split("\"")[3]);
                sb.append(", ");
            }
        }
        postKeyword.setKeyword(sb.toString());
        keywordRepository.save(postKeyword);

        PostHashTag postHashTag = new PostHashTag();
        postHashTag.setPost(post);
        postHashTag.setPostKeyword(postKeyword);
        hashTagRepository.save(postHashTag);

    }

    public Post findById(Long postId) {
        return postRepository.findById(postId).orElseThrow(null);
    }

    public void delete(Post post, PostKeyword postKeyword, PostHashTag postHashTag) {
        hashTagRepository.delete(postHashTag);
        keywordRepository.delete(postKeyword);
        postRepository.delete(post);
    }

    public void saveNewPostDto(Post post, PostDto postDto) {
        postDto.setSubject(post.getSubject());
        postDto.setContent(post.getContent());
        postDto.setContentHTML(post.getContentHTML());
    }

    public void modify(Post post, PostKeyword postKeyword, PostHashTag postHashTag,PostDto postDto, String keyword) {
        post.setSubject(postDto.getSubject());
        post.setContent(postDto.getContent());
        post.setContentHTML(postDto.getContentHTML());
        postRepository.save(post);

        postKeyword.setKeyword(keyword);
        keywordRepository.save(postKeyword);

        postHashTag.setPost(post);
        postHashTag.setPostKeyword(postKeyword);
        hashTagRepository.save(postHashTag);
    }
}
