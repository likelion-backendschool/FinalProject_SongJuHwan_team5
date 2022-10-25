package com.ll.finalProject.week2.controller;

import com.ll.finalProject.week2.domain.CartItem;
import com.ll.finalProject.week2.domain.Member;
import com.ll.finalProject.week2.domain.OrderItem;
import com.ll.finalProject.week2.domain.Ordered;
import com.ll.finalProject.week2.service.CartItemService;
import com.ll.finalProject.week2.service.MemberService;
import com.ll.finalProject.week2.service.OrderItemService;
import com.ll.finalProject.week2.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final MemberService memberService;
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @GetMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String createOrder(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Member member = memberService.findByUserName(user.getUsername());
        orderService.createFromCart(member);
        return "redirect:/order/list";
    }

    @GetMapping("/list")
    @PreAuthorize("isAuthenticated()")
    public String orderList(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Member member = memberService.findByUserName(user.getUsername());
        List<Ordered> orderedList = orderService.findAllByMember(member);
        model.addAttribute("orderList", orderedList);
        return "order/list";
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("isAuthenticated()")
    public String orderDetail(@PathVariable Long orderId, Model model){
        Ordered ordered = orderService.findById(orderId);
        List<OrderItem> orderItemList = orderItemService.findAllByOrder(ordered);
        model.addAttribute("order", ordered);
        model.addAttribute("orderItemList", orderItemList);
        return "order/detail";

    }
}
