package com.ll.finalProject.week2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Ordered extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_UUID")
    private Member member;

    @Column(name = "ORDER_STATUS")
    private String readyStatus;

    @Column(name = "ORDER_IS_PAID")
    private String isPaid;

    @Column(name = "ORDER_NAME")
    private String name;

}
