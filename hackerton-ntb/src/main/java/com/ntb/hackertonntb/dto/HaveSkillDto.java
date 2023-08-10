package com.ntb.hackertonntb.dto;

import com.ntb.hackertonntb.domain.entity.HaveSkill;
import com.ntb.hackertonntb.domain.entity.Skills;
import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class HaveSkillDto {
    private int id;
    private Skills skills;

    public HaveSkill toEntity(){
        HaveSkill build = HaveSkill.builder()
                .id(id)
                .skills(skills)
                .build();
        return build;
    }

    @Builder
    public HaveSkillDto(int id, Skills skills){
        this.id = id;
        this.skills = skills;
    }
}