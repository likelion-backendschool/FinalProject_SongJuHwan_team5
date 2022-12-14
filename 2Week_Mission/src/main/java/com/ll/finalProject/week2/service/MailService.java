package com.ll.finalProject.week2.service;

import com.ll.finalProject.week2.dto.Member.MemberDto;
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

    public void sendPasswordEmail(String email, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("jootangbooks@gmail.com");
        message.setTo(email);
        message.setSubject("Jootangbooks계정의 임시비밀번호입니다.");
        message.setText("임시비밀번호는 " + newPassword + " 입니다.\n" +
                "비밀번호를 반드시 변경해주세요.");
        emailSender.send(message);
    }
}
