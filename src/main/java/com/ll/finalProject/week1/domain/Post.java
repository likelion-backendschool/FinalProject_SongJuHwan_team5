package com.ll.finalProject.week1.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Post extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_UUID")
    private Member member;

    @Column(name = "POST_SUBJECT")
    private String subject;

    @Column(name = "POST_CONTENT")
    private String content;

    @Column(name = "POST_CONTENT_HTML")
    private String contentHTML;
}
