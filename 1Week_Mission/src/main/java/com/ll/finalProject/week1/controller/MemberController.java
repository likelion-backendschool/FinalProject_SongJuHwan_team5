package com.ll.finalProject.week1.controller;

import com.ll.finalProject.week1.domain.Member;
import com.ll.finalProject.week1.dto.Member.MemberDto;
import com.ll.finalProject.week1.dto.Member.MemberModifyDto;
import com.ll.finalProject.week1.dto.Member.MemberModifyPasswordDto;
import com.ll.finalProject.week1.service.MailService;
import com.ll.finalProject.week1.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;


@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/join")
    public String loginMember(MemberDto memberDto){
        return "member/join";
    }

    @PostMapping("/join")
    public String loginMember(@Valid MemberDto memberDto, BindingResult bindingResult){
        //회원가입 오류 검사
        if(bindingResult.hasErrors()){
            return "member/join";
        }

        if(!memberDto.getPassword().equals(memberDto.getPasswordConfirm())){
            bindingResult.rejectValue("passwordConfirm", "passwordInCorrect",
                    "비밀번호가 일치하지 않습니다.");
            return "member/join";
        }

        try{
            memberService.create(memberDto);
            mailService.sendWelcomeMail(memberDto);
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

    @GetMapping("/myPage")
    @PreAuthorize("isAuthenticated()")
    public String myPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Member member = memberService.findByUserName(user.getUsername());
        model.addAttribute("member" , member);
        return "/member/myPage";
    }

    @GetMapping("/modify")
    @PreAuthorize("isAuthenticated()")
    public String modifyMember(Model model, MemberModifyDto memberModifyDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Member member = memberService.findByUserName(user.getUsername());
        memberService.saveNewMemberDto(member, memberModifyDto);
        model.addAttribute("member", member);
        model.addAttribute("memberModifyDto", memberModifyDto);

        return "/member/modify";
    }

    @PostMapping("/modify")
    @PreAuthorize("isAuthenticated()")
    public String modifyMember(@Valid MemberModifyDto memberModifyDto, BindingResult bindingResult){
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

    @GetMapping("/register")
    @PreAuthorize("isAuthenticated()")
    public String register(){
        return "member/register";
    }

    @PostMapping("/register")
    @PreAuthorize("isAuthenticated()")
    public String register(@RequestParam String nickName){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Member member = memberService.findByUserName(user.getUsername());
        memberService.register(member, nickName);
        return "redirect:/member/myPage";
    }

    @GetMapping("/modifyPassword")
    @PreAuthorize("isAuthenticated()")
    public String modifyPassword(MemberModifyPasswordDto memberModifyPasswordDto){
        return "member/modifyPassword";
    }

    @PostMapping("/modifyPassword")
    @PreAuthorize("isAuthenticated()")
    public String modifyPassword(@Valid MemberModifyPasswordDto memberModifyPasswordDto, BindingResult bindingResult){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Member member = memberService.findByUserName(user.getUsername());
        if(!passwordEncoder.matches(memberModifyPasswordDto.getOldPassword(), member.getPassword())){
            bindingResult.rejectValue("oldPassword", "passwordIncorrect",
                    "원래 비밀번호가 아닙니다.");
            return "member/modifyPassword";
        }
        if(memberModifyPasswordDto.getOldPassword().equals(memberModifyPasswordDto.getPassword())){
            bindingResult.rejectValue("password", "samePassword",
                    "원래 비밀번호와 동일한 비밀번호 입니다.");
            return "member/modifyPassword";
        }

        if(!memberModifyPasswordDto.getPassword().equals(memberModifyPasswordDto.getPasswordConfirm())){
            bindingResult.rejectValue("passwordConfirm", "passWordInCorrect",
                    "비밀번호가 일치하지 않습니다.");
            return "member/modifyPassword";
        }

        memberService.modifyPassword(member, memberModifyPasswordDto.getPassword());

        return "redirect:/member/logout";
    }

    @GetMapping("/findUsername")
    public String findId(){
        return "member/findUserName";
    }

    @PostMapping("/findUsername")
    public String findId(@RequestParam String email, Model model){
        String result = memberService.findUserName(email);
        model.addAttribute("result", result);
        return "member/findUserNameResult";
    }

    @GetMapping("/findPassword")
    public String findPassword(){
        return "member/findPassword";
    }

    @PostMapping("/findPassword")
    public String findPassword(@RequestParam String userName, @RequestParam String email, Model model){
        String result = memberService.findPassword(userName, email);
        model.addAttribute("result", result);
        return "member/findPasswordResult";
    }

}
