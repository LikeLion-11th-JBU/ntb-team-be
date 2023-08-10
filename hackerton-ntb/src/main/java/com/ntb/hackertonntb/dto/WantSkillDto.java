package com.ntb.hackertonntb.dto;

import com.ntb.hackertonntb.domain.entity.Skills;
import com.ntb.hackertonntb.domain.entity.WantSkill;
import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class WantSkillDto {
    private int id;
    private Skills skills;

    public WantSkill toEntity(){
        WantSkill build = WantSkill.builder()
                .id(id)
                .skills(skills)
                .build();
        return build;
    }

    @Builder
    public WantSkillDto(int id, Skills skills){
        this.id = id;
        this.skills = skills;
    }
}