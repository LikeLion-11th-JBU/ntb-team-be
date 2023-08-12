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
    private Skills haveSkills;

    public HaveSkill toEntity(Skills haveSkills){
        HaveSkill build = HaveSkill.builder()
                .haveSkills(haveSkills)
                .build();
        return build;
    }

    @Builder
    public HaveSkillDto(Skills haveSkills){
        this.haveSkills = haveSkills;
    }
}