package com.ll.finalProject.week2.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class PostDto {
    @NotEmpty(message = "제목을 입력해주세요.")
    private String subject;

    private String content;

    private String contentHTML;

    private String keywords;
}
