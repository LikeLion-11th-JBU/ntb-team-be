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
    private Skills wantSkills;

    public WantSkill toEntity(Skills wantSkills){
        WantSkill build = WantSkill.builder()
                .wantSkills(wantSkills)
                .build();
        return build;
    }

    @Builder
    public WantSkillDto(Skills wantSkills){
        this.wantSkills = wantSkills;
    }
}