package com.ntb.hackertonntb.dto;

import com.ntb.hackertonntb.domain.entity.Category;
import com.ntb.hackertonntb.domain.entity.Gesipan;
import com.ntb.hackertonntb.domain.entity.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDto {

    private int id;

    private LocalDateTime created;

    @NotBlank(message = "아이디는 필수 입력값입니다.")
    @Pattern(regexp = "^[a-z0-9]{4,20}$", message = "아이디는 영어 소문자와 숫자만 사용하여 4~20자리여야 합니다.")
    private String loginId;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$", message = "비밀번호는 8~16자리수여야 합니다. 영문 대소문자, 숫자, 특수문자를 1개 이상 포함해야 합니다.")
    private String password;

    @NotBlank(message = "소개는 필수 입력값입니다.")
    private String introduce;

    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String name;
    private byte userimage;
    private String openemail;

    @NotBlank(message = "오픈 채팅 주소는 필수 입력값입니다.")
    private String openChat;

    @Email(message = "이메일 형식으로 제출 해야합니다.")
    private String email;

    private String profileName;

    private String profilePath;

    @OneToMany(mappedBy = "users")
    private List<Category> categories;


    public User toEntity(){
        User build = User.builder()
                .loginId(loginId)
                .password(password)
                .introduce(introduce)
                .name(name)
                .openChat(openChat)
                .email(email)
                .profileName(profileName)
                .profilePath(profilePath)
                .build();  //build() 가 객체를 생성해 돌려준다.
        return build;
    }

    @Builder
    public UserDto(
            String loginId,
            String password,
            String introduce,
            String name,
            String openChat,
            LocalDateTime created,
            String email,
            String profileName,
            String profilePath,
            List<Category> categories
    ){
        this.loginId = loginId;
        this.password = password;
        this.password = password;
        this.introduce = introduce;
        this.name = name;
        this.openChat = openChat;
        this.created = created;
        this.email = email;
        this.profileName = profileName;
        this.profilePath = profilePath;
        this.categories = categories;
    }

    @OneToMany(mappedBy = "user")
    private List<Gesipan> gesipan;
}
