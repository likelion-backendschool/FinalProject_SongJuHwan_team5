package com.ll.finalProject.week1.service;

import com.ll.finalProject.week1.domain.Member;
import com.ll.finalProject.week1.dto.Member.MemberDto;
import com.ll.finalProject.week1.dto.Member.MemberModifyDto;
import com.ll.finalProject.week1.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    public void create(MemberDto memberDto) {
        Member member = new Member();
        member.setUserName(memberDto.getUserName());
        member.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        member.setType("일반 회원");
        member.setEmail(memberDto.getEmail());
        member.setAuthLevel(3);
        memberRepository.save(member);
    }

    public void update(MemberModifyDto memberModifyDto, Member member) {
        member.setNickName(memberModifyDto.getNickName());
        member.setEmail(memberModifyDto.getEmail());
        memberRepository.save(member);
    }

    public Member findByUserName(String username) {
        return memberRepository.findByUserName(username).orElseThrow(null);
    }

    public MemberModifyDto saveNewMemberDto(Member member, MemberModifyDto memberModifyDto) {
        memberModifyDto.setEmail(member.getEmail());
        memberModifyDto.setNickName(member.getNickName());

        return memberModifyDto;
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public String findUserName(String email) {
        List<Member> memberList = memberRepository.findAll();
        for(Member member : memberList){
            if(member.getEmail().equals(email)){
                return member.getUserName();
            }
        }
        String wrong = "해당 e-mail로 등록된 ID가 존재하지 않습니다.";
        return wrong;
    }

    public void modifyPassword(Member member, String password) {
        member.setPassword(passwordEncoder.encode(password));
        memberRepository.save(member);
    }

    public String findPassword(String userName, String email) {
        List<Member> memberList = memberRepository.findAll();
        for(Member member : memberList) {
            if(member.getUserName().equals(userName) && member.getEmail().equals(email)){
                //랜덤 문자열 생성
                String alphaNum = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
                int alphaNumLength = alphaNum.length();
                Random random = new Random();
                StringBuffer code = new StringBuffer();
                for (int i = 0; i < 20; i++) {
                    code.append(alphaNum.charAt(random.nextInt(alphaNumLength)));
                }
                //
                member.setPassword(passwordEncoder.encode(code.toString()));
                memberRepository.save(member);
                mailService.sendPasswordEmail(member.getEmail() , code.toString());
                return "가입하신 email로 임시비밀번호를 발송했습니다.";
            }
        }
        return "입력하신 정보와 일치하는 계정이 없습니다.";
    }

    public void register(Member member, String nickName) {
        member.setNickName(nickName);
        member.setType("작가 회원");
        memberRepository.save(member);
    }
}
