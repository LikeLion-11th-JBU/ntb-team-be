package com.ntb.hackertonntb.domain.entity;

import com.ntb.hackertonntb.dto.UserDto;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends TimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(length = 45, nullable = false, unique = true)
    private String loginId;

    @Column(length = 255, nullable = false)
    private String password;

    @Column(length = 245, nullable = false)
    private String introduce;

    @Column(length = 45, nullable = false)
    private String name;

    @Column(length = 45, nullable = false)
    private String openChat;

    @Column(length = 45, nullable = false)
    private String email;

    @Column(length = 245, nullable = false)
    private String profileName;

    @Column(length = 245, nullable = false)
    private String profilePath;

    @Column
    private String role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users", fetch = FetchType.LAZY)
    private List<Category> categories = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users", fetch = FetchType.LAZY)
    private List<Gesipan> gesipans = new ArrayList<>();


    @Builder
    public User(
            String loginId,
            String password,
            String introduce,
            String name,
            String openChat,
            String email,
            String profileName,
            String profilePath,
            String role,
            List<Category> categories
    ){
        this.loginId = loginId;
        this.password = password;
        this.introduce = introduce;
        this.name = name;
        this.openChat = openChat;
        this.email = email;
        this.profileName = profileName;
        this.profilePath = profilePath;
        this.role = role;
        this.categories = categories;
    }


}
