package com.ll.finalProject.week1.service;

import com.ll.finalProject.week1.domain.PostKeyword;
import com.ll.finalProject.week1.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;

    public PostKeyword findById(Long postId) {
        return keywordRepository.findById(postId).orElseThrow(null);
    }
}
