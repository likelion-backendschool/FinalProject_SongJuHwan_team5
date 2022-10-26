package com.ll.finalProject.week2.repository;

import com.ll.finalProject.week2.domain.MyBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyBookRepository extends JpaRepository<MyBook, Long> {
}
