package com.ll.finalProject.week2.service;

import com.ll.finalProject.week2.domain.PostKeyword;
import com.ll.finalProject.week2.repository.KeywordRepository;
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
