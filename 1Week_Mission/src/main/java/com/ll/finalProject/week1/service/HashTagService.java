package com.ll.finalProject.week1.service;

import com.ll.finalProject.week1.domain.PostHashTag;
import com.ll.finalProject.week1.repository.HashTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HashTagService {

    private final HashTagRepository hashTagRepository;

    public PostHashTag findByPostId(Long postId) {
       return hashTagRepository.findByPostId(postId).orElseThrow(null);
    }

    public List<PostHashTag> findAll() {
        return hashTagRepository.findAll();
    }
}
