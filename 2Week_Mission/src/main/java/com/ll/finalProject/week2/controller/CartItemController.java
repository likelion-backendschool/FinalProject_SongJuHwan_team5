package com.ll.finalProject.week2.controller;

import com.ll.finalProject.week2.domain.CartItem;
import com.ll.finalProject.week2.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartItemController {

    private final CartItemService cartItemService;

    @GetMapping("/list")
    public String cartItemList(Model model){
        List<CartItem> cartItemList = cartItemService.findAll();
        model.addAttribute("cartItemList", cartItemList);
        return "cart/list";
    }
}
