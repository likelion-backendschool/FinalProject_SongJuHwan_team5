package com.ll.finalProject.week2.dto.Member;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberModifyPasswordDto {

    @NotEmpty(message = "원래 비밀번호를 입력해주세요.")
    private String oldPassword;

    @NotEmpty(message = "새로운 비밀번호를 입력해주세요.")
    private String password;

    @NotEmpty(message = "새로운 비밀번호를 확인해주세요.")
    private String passwordConfirm;
}
