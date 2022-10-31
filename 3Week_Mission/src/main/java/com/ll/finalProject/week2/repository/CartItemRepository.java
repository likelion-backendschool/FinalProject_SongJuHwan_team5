package com.ll.finalProject.week2.repository;

import com.ll.finalProject.week2.domain.CartItem;
import com.ll.finalProject.week2.domain.Member;
import com.ll.finalProject.week2.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByMemberAndProduct(Member member, Product product);

    List<CartItem> findAllByMemberId(Long memberId);
}
