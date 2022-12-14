package com.ll.finalProject.week2.service;

import com.ll.finalProject.week2.domain.Member;
import com.ll.finalProject.week2.dto.Member.MemberDto;
import com.ll.finalProject.week2.dto.Member.MemberModifyDto;
import com.ll.finalProject.week2.repository.MemberRepository;
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
    private final CashService cashService;

    public void create(MemberDto memberDto) {
        Member member = new Member();
        member.setUserName(memberDto.getUserName());
        member.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        member.setType("μΌλ° νμ");
        member.setEmail(memberDto.getEmail());
        member.setAuthLevel(3);
        member.setRestCash(0);
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
                return member.getUsername();
            }
        }
        String wrong = "ν΄λΉ e-mailλ‘ λ±λ‘λ IDκ° μ‘΄μ¬νμ§ μμ΅λλ€.";
        return wrong;
    }

    public void modifyPassword(Member member, String password) {
        member.setPassword(passwordEncoder.encode(password));
        memberRepository.save(member);
    }

    public String findPassword(String userName, String email) {
        List<Member> memberList = memberRepository.findAll();
        for(Member member : memberList) {
            if(member.getPassword().equals(userName) && member.getEmail().equals(email)){
                //λλ€ λ¬Έμμ΄ μμ±
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
                return "κ°μνμ  emailλ‘ μμλΉλ°λ²νΈλ₯Ό λ°μ‘νμ΅λλ€.";
            }
        }
        return "μλ ₯νμ  μ λ³΄μ μΌμΉνλ κ³μ μ΄ μμ΅λλ€.";
    }

    public void register(Member member, String nickName) {
        member.setNickName(nickName);
        member.setType("μκ° νμ");
        memberRepository.save(member);
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(null);
    }

    public void addCash(Member member, int cash, String eventType){
        cashService.addCash(member, cash, eventType);
        int newRestCash = member.getRestCash() + cash;
        member.setRestCash(newRestCash);;
        memberRepository.save(member);
    }
}
