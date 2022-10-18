package com.ll.finalProject.week1.service;

import com.ll.finalProject.week1.domain.Member;
import com.ll.finalProject.week1.dto.MemberDto;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {

    private JavaMailSender emailSender;

    public void sendWelcomeMail(MemberDto memberDto){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("jootangbooks@gmail.com");
        message.setTo(memberDto.getEmail());
        message.setSubject("JootangBooks에 가입하신 걸 환영합니다.");
        message.setText("JootangBooks의 멤버가 되신 걸 환영합니다.");
        emailSender.send(message);
    }
}
