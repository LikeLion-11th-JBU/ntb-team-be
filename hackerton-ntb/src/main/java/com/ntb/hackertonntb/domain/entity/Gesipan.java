package com.ntb.hackertonntb.domain.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gesipan extends TimeEntity{
    @Id
    @GeneratedValue
    private int id;

    @Column(length = 500, nullable = false)
    private String detail;

    @Column(length = 100, nullable = false)
    private String comment;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User users;

    @Builder
    public Gesipan(int id, String detail, String comment, User users) {
        this.id = id;
        this.detail = detail;
        this.comment = comment;
        this.users = users;
    }
}