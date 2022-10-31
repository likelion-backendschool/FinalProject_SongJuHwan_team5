package com.ll.finalProject.week2.domain;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"MEMBER_UUID", "PRODUCT_UUID"}))
public class CartItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_UUID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_UUID")
    private Product product;

}
