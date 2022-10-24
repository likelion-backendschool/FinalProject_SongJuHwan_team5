package com.ll.finalProject.week2.controller;

import com.ll.finalProject.week2.domain.CartItem;
import com.ll.finalProject.week2.domain.Member;
import com.ll.finalProject.week2.domain.Product;
import com.ll.finalProject.week2.service.CartItemService;
import com.ll.finalProject.week2.service.MemberService;
import com.ll.finalProject.week2.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartItemController {

    private final CartItemService cartItemService;
    private final ProductService productService;
    private final MemberService memberService;

    @GetMapping("/list")
    @PreAuthorize("isAuthenticated()")
    public String cartItemList(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Member member = memberService.findByUserName(user.getUsername());
        List<CartItem> cartItemList = cartItemService.findAll();
        model.addAttribute("member", member);
        model.addAttribute("cartItemList", cartItemList);
        return "cart/list";
    }

    @GetMapping("/add/{productId}")
    @PreAuthorize("isAuthenticated()")
    public String addCartList(@PathVariable Long productId, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Member member = memberService.findByUserName(user.getUsername());
        Product product = productService.findById(productId);
        try{
            cartItemService.addItem(member, product);
            return "redirect:/cart/list";
        } catch (DataIntegrityViolationException e){
            model.addAttribute("message", "이미 담긴 상품입니다.");
            return "error/alertAndBack";
        }
    }

    @GetMapping("/remove/{productId}")
    @PreAuthorize("isAuthenticated()")
    public String removeCartList(@PathVariable Long productId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Member member = memberService.findByUserName(user.getUsername());
        Product product = productService.findById(productId);
        cartItemService.delete(member, product);
        return "redirect:/cart/list";
    }
}
