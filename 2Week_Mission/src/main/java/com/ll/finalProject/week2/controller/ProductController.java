package com.ll.finalProject.week2.controller;

import com.ll.finalProject.week2.domain.Member;
import com.ll.finalProject.week2.domain.Product;
import com.ll.finalProject.week2.service.MemberService;
import com.ll.finalProject.week2.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final MemberService memberService;

    @GetMapping("/list")
    public String productList(Model model){
        List<Product> productList = productService.findAll();
        model.addAttribute("productList", productList);
        return "product/list";
    }

}
