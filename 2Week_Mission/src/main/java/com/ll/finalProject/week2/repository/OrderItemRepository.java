package com.ll.finalProject.week2.repository;

import com.ll.finalProject.week2.domain.OrderItem;
import com.ll.finalProject.week2.domain.Ordered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findAllByOrdered(Ordered ordered);
}
