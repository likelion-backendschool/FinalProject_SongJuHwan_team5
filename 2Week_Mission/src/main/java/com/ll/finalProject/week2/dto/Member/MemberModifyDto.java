package com.ll.finalProject.week2.dto.Member;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class MemberModifyDto {

    @Size(min = 3 , max = 25, message = "닉네임의 최소길이는 3 글자, 최대길이는 25 글자 입니다.")
    private String nickName;

    @Email
    @NotEmpty(message = "이메일은 필수사항입니다.")
    private String email;


}
