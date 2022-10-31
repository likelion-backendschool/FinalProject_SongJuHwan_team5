package com.ll.finalProject.week2.service;

import com.ll.finalProject.week2.domain.OrderItem;
import com.ll.finalProject.week2.domain.Ordered;
import com.ll.finalProject.week2.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;


    public List<OrderItem> findAllByOrder(Ordered ordered) {
        return orderItemRepository.findAllByOrdered(ordered);
    }
}
