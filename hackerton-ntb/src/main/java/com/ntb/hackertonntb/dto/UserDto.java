package com.ntb.hackertonntb.dto;

import com.ntb.hackertonntb.domain.entity.Category;
import com.ntb.hackertonntb.domain.entity.Gesipan;
import com.ntb.hackertonntb.domain.entity.User;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDto {
    private int id;
    private LocalDateTime created;
    private String loginid;
    private String loginpassword;
    private String introduce;
    private String name;
    private byte userimage;
    private String openemail;

    public User toEntity(){
        User build = User.builder()
                .id(id)
                .loginid(loginid)
                .loginpassword(loginpassword)
                .introduce(introduce)
                .name(name)
                .userimage(userimage)
                .openeamil(openemail)
                .build();
        return build;
    }

    @Builder
    public UserDto(int id, String loginid, String loginpassword, String introduce, String name, LocalDateTime created, byte userimage, String openemail){
        this.id = id;
        this.loginid = loginid;
        this.loginpassword = loginpassword;
        this.introduce = introduce;
        this.name = name;
        this.created = created;
        this.userimage = userimage;
        this.openemail = openemail;
    }

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "user")
    private List<Gesipan> gesipan;
}
