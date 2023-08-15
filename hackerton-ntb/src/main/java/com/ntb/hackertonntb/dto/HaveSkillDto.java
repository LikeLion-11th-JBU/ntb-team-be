package com.ntb.hackertonntb.dto;

import com.ntb.hackertonntb.domain.entity.HaveSkill;
import com.ntb.hackertonntb.domain.entity.Skills;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class HaveSkillDto {
    private int id;

    public HaveSkill toEntity(){
        HaveSkill build = HaveSkill.builder()
                .id(id)
                .build();
        return build;
    }

    @Builder
    public HaveSkillDto(int id){
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "skills_id")
    private Skills skills;
}