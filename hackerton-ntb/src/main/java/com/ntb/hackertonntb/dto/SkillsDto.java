package com.ntb.hackertonntb.dto;

import com.ntb.hackertonntb.domain.entity.*;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class SkillsDto {
    private int id;

    @NotBlank(message = "재능은 필수 입력 값 입니다.")
    private String skillname;


    private SmallCategory smallCategories;

    private List<HaveSkill> haveSkills;

    private List<WantSkill> wantSkills;


    public Skills toEntity(SmallCategory smallCategories){
        Skills build = Skills.builder()
                .skillname(skillname)
                .smallCategories(smallCategories)
                .build();
        return build;
    }

    @Builder
    public SkillsDto(String skillname,
                     SmallCategory smallCategories,
                     List<HaveSkill> haveSkills,
                     List<WantSkill> wantSkills){  // 리스트들을 받는 생성자 추가
        this.skillname = skillname;
        this.smallCategories = smallCategories;
        this.haveSkills = haveSkills;
        this.wantSkills = wantSkills;
    }

}