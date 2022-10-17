package com.ll.finalProject.week1.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column(unique = true, name = "USER_NAME")
    private String userName;

    @Column( name = "USER_PASSWORD")
    private String password;

    @Column(unique = true, name = "USER_NICKNAME")
    private String nickName;

    @Column(unique = true, name = "USER_EMAIL")
    private String email;

    @Column( name = "USER_AUTH")
    private Integer authLevel;

}