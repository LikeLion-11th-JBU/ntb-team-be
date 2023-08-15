package com.ntb.hackertonntb.dto;

import com.ntb.hackertonntb.domain.entity.Gesipan;
import com.ntb.hackertonntb.domain.entity.User;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class GesipanDto {
    private int id;
    private LocalDateTime date;
    private String detail;
    private String comment;

    public Gesipan toEntity(){
        Gesipan build = Gesipan.builder()
                .id(id)
                .detail(detail)
                .comment(comment)
                .build();
        return build;
    }

    @Builder
    public GesipanDto(int id, String detail, String comment, LocalDateTime date){
        this.id = id;
        this.detail = detail;
        this.comment = comment;
        this.date = date;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}