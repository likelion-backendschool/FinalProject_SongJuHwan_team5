package com.ll.finalProject.week2.service;

import com.ll.finalProject.week2.domain.CartItem;
import com.ll.finalProject.week2.domain.Member;
import com.ll.finalProject.week2.domain.Ordered;
import com.ll.finalProject.week2.domain.OrderItem;
import com.ll.finalProject.week2.repository.OrderItemRepository;
import com.ll.finalProject.week2.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartItemService cartItemService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;


    public void createFromCart(Member member) {
        List<CartItem> cartItemList = cartItemService.getItemsByMember(member);
        Ordered ordered = new Ordered();
        ordered.setMember(member);
        orderRepository.save(ordered);
        for (CartItem cartItem : cartItemList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setOrdered(ordered);
            orderItemRepository.save(orderItem);
            cartItemService.delete(cartItem);
        }
    }
}
