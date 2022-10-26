package com.ll.finalProject.week2.service;

import com.ll.finalProject.week2.domain.CartItem;
import com.ll.finalProject.week2.domain.Member;
import com.ll.finalProject.week2.domain.Product;
import com.ll.finalProject.week2.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;


    public List<CartItem> findAll() {
        return cartItemRepository.findAll();
    }

    public void addItem(Member member, Product product) {
        CartItem cartItem = new CartItem();
        cartItem.setMember(member);
        cartItem.setProduct(product);
        cartItemRepository.save(cartItem);
    }

    public void delete(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }

    public void delete(Member member, Product product) {
        CartItem cartItem = findByMemberAndProduct(member,product);
        cartItemRepository.delete(cartItem);
    }

    public CartItem findByMemberAndProduct(Member member, Product product) {
        return cartItemRepository.findByMemberAndProduct(member, product);
    }

    public List<CartItem> getItemsByMember(Member member) {
        return cartItemRepository.findAllByMemberId(member.getId());
    }
}
