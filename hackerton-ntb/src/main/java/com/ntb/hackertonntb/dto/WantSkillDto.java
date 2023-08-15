package com.ntb.hackertonntb.dto;

import com.ntb.hackertonntb.domain.entity.Skills;
import com.ntb.hackertonntb.domain.entity.WantSkill;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class WantSkillDto {
    private int id;

    public WantSkill toEntity(){
        WantSkill build = WantSkill.builder()
                .id(id)
                .build();
        return build;
    }

    @Builder
    public WantSkillDto(int id){
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "skills_id")
    private Skills skills;
}