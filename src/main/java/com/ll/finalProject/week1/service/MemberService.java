package com.ll.finalProject.week1.service;

import com.ll.finalProject.week1.domain.Member;
import com.ll.finalProject.week1.dto.MemberDto;
import com.ll.finalProject.week1.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void create(MemberDto memberDto) {
        Member member = new Member();
        member.setUserName(memberDto.getUserName());
        member.setNickName(memberDto.getNickName());
        member.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        member.setEMail(memberDto.getEMail());
        member.setAuthLevel(3);
        memberRepository.save(member);
    }

    public void update(MemberDto memberDto, Member member) {
        member.setUserName(memberDto.getUserName());
        member.setNickName(memberDto.getNickName());
        member.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        member.setEMail(memberDto.getEMail());
        memberRepository.save(member);
    }

    public Member findByUserName(String username) {
        return memberRepository.findByUserName(username).orElseThrow(null);
    }

    public MemberDto saveNewMemberDto(Member member, MemberDto memberDto) {
        memberDto.setUserName(member.getUserName());
        memberDto.setEMail(member.getEMail());
        memberDto.setNickName(member.getNickName());
        memberDto.setPassword(member.getPassword());
        memberDto.setPasswordConfirm(member.getPassword());

        return memberDto;
    }
}
