package com.ll.finalProject.week1.repository;

import com.ll.finalProject.week1.domain.PostHashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HashTagRepository extends JpaRepository<PostHashTag, Long> {
    Optional<PostHashTag> findByPostId(Long postId);
}
