package com.ll.finalProject.week1.controller;

import com.ll.finalProject.week1.domain.Member;
import com.ll.finalProject.week1.dto.MemberDto;
import com.ll.finalProject.week1.dto.MemberModifyDto;
import com.ll.finalProject.week1.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String memberLogin(MemberDto memberDto){
        return "member/join";
    }

    @PostMapping("/join")
    public String memberLogin(@Valid MemberDto memberDto, BindingResult bindingResult){
        //회원가입 오류 검사
        if(bindingResult.hasErrors()){
            return "member/join";
        }

        if(!memberDto.getPassword().equals(memberDto.getPasswordConfirm())){
            bindingResult.rejectValue("passwordConfirm", "passWordInCorrect",
                    "비밀번호가 일치하지 않습니다.");
            return "member/join";
        }

        try{
            memberService.create(memberDto);
        } catch(DataIntegrityViolationException e){
            e.printStackTrace();
            bindingResult.reject("joinFailed", "이미 등록된 사용자입니다.");
            return "member/join";
        } catch(Exception e){
            e.printStackTrace();
            bindingResult.reject("joinFailed", e.getMessage());
            return "member/join";
        }

        return "redirect:/";
    }
    @GetMapping("/login")
    public String login(){
        return "/member/login";
    }

    @GetMapping("/modify")
    @PreAuthorize("isAuthenticated()")
    public String memberUpdate(Model model, MemberModifyDto memberModifyDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Member member = memberService.findByUserName(user.getUsername());
        MemberModifyDto newMemberModifyDto = memberService.saveNewMemberDto(member, memberModifyDto);
        model.addAttribute("memberModifyDto", memberModifyDto);

        return "/member/modify";
    }

    @PostMapping("/modify")
    @PreAuthorize("isAuthenticated()")
    public String memberUpdate(@Valid MemberModifyDto memberModifyDto, BindingResult bindingResult){
        //현재 로그인 된 사용자
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Member member = memberService.findByUserName(user.getUsername());
        //회원정보 수정 시 오류 검사
        if(bindingResult.hasErrors()){
            return "member/modify";
        }

        try{
            memberService.update(memberModifyDto, member);
        } catch(DataIntegrityViolationException e){
            e.printStackTrace();
            bindingResult.reject("joinFailed", "이미 등록된 사용자입니다.");
            return "member/modify";
        } catch(Exception e){
            e.printStackTrace();
            bindingResult.reject("joinFailed", e.getMessage());
            return "member/modify";
        }

        return "redirect:/member/logout";
    }

    @GetMapping("/findUsername")
    public String findId(){
        return "member/findUserName";
    }

    @PostMapping("/findUsername")
    public String findId(@RequestParam String email, Model model){
        System.out.println(email);
        String result = memberService.findUserName(email);
        model.addAttribute("result", result);
        return "member/findUserNameResult";
    }


}
