package com.ll.finalProject.week1.dto.Member;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class MemberDto {
    @Size(min = 3 , max = 25, message = "ID의 최소길이는 3 글자, 최대길이는 25 글자 입니다.")
    @NotEmpty(message = "ID는 필수사항입니다.")
    private String userName;

    @NotEmpty(message = "비밀번호는 필수사항입니다.")
    private String password;

    @NotEmpty(message = "비밀번호 확인은 필수사항입니다.")
    private String passwordConfirm;

    @Email
    @NotEmpty(message = "이메일은 필수사항입니다.")
    private String email;


}
