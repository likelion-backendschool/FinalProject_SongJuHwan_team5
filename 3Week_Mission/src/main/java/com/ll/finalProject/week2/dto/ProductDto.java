package com.ll.finalProject.week2.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ProductDto {
    @NotEmpty(message = "상품의 이름은 필수사항입니다.")
    private String subject;

    @NotEmpty(message = "상품의 가격은 필수사항입니다.")
    private Integer price;
}
