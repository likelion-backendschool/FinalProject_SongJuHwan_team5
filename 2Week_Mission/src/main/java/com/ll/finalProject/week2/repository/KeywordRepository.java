package com.ll.finalProject.week2.repository;

import com.ll.finalProject.week2.domain.PostKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordRepository extends JpaRepository<PostKeyword, Long> {
}
