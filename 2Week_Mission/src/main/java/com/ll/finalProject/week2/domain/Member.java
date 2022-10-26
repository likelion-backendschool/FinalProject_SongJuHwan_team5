package com.ll.finalProject.week2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Member extends BaseEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column(unique = true, name = "USER_NAME")
    private String userName;

    @Column( name = "USER_PASSWORD")
    private String password;

    @Column( name = "USER_NICKNAME")
    private String nickName;

    @Column( name = "USER_TYPE")
    private String type;

    @Column(unique = true, name = "USER_EMAIL")
    private String email;

    @Column( name = "USER_AUTH")
    private Integer authLevel;

    @Column( name = "REST_CASH")
    private Integer restCash;

}