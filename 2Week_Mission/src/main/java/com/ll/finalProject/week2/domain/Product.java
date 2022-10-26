package com.ll.finalProject.week2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_UUID")
    private Member member;

    @Column(name = "PRODUCT_SUBJECT")
    private String subject;

    @Column(name = "PRODUCT_PRICE")
    private Integer price;

}
