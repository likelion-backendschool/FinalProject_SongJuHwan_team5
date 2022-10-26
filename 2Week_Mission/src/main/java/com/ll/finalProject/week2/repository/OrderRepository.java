package com.ll.finalProject.week2.repository;

import com.ll.finalProject.week2.domain.Member;
import com.ll.finalProject.week2.domain.Ordered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Ordered, Long> {
    List<Ordered> findAllByMember(Member member);
}
