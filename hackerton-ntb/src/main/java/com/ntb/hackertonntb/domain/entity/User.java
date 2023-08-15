package com.ntb.hackertonntb.domain.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends TimeEntity{
    @Id
    @GeneratedValue
    private int id;

    @Column(length = 45, nullable = false)
    private String loginid;

    @Column(length = 45, nullable = false)
    private String loginpassword;

    @Column(length = 245, nullable = false)
    private String introduce;

    @Column(length = 45, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String openeamil;

    @Lob
    private byte[] userimage;

    @Builder
    public User(int id, String loginid, String loginpassword, String introduce, String name, byte userimage, String openeamil){
        this.id = id;
        this.loginid = loginid;
        this.loginpassword = loginpassword;
        this.introduce = introduce;
        this.name = name;
        this.openeamil = openeamil;
        this.userimage = new byte[]{userimage};
    }
}
