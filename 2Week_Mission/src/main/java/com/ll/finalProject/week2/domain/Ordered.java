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
    private Boolean isPaid;

    @Column(name = "ORDER_IS_CANCELED")
    private Boolean isCanceled;

    @Column(name = "ORDER_NAME")
    private String name;

    @Column(name = "ORDER_TOTAL_PRICE")
    private Integer calculatePayPrice;

    public boolean isPayable(){
        if(isPaid) return false;
        if(isCanceled) return false;

        return true;
    }
}
