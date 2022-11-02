package com.ll.finalProject.week2.controller;

import com.ll.finalProject.week2.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/adm")
public class AdminController {

    @GetMapping("/home/main")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String main(){
        return "admin/main";
    }

}
