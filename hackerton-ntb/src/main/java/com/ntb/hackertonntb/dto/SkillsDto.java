package com.ntb.hackertonntb.dto;

import com.ntb.hackertonntb.domain.entity.Category;
import com.ntb.hackertonntb.domain.entity.Skills;
import com.ntb.hackertonntb.domain.entity.SmallCategory;
import lombok.*;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class SkillsDto {
    private int id;

    @NotBlank(message = "재능은 필수 입력 값 입니다.")
    private String skillname;

    @NotBlank(message = "재능은 필수 입력 값 입니다.")
    private String skillname2;

    @NotBlank(message = "재능은 필수 입력 값 입니다.")
    private String skillname3;

    private SmallCategory smallCategories;

    public Skills toEntity(){
        Skills build = Skills.builder()
                .id(id)
                .skillname(skillname)
                .skillname2(skillname2)
                .skillname3(skillname3)
                .smallCategories(smallCategories)
                .build();
        return build;
    }

    @Builder
    public SkillsDto(int id, String skillname, String skillname2, String skillname3, SmallCategory smallCategories){
        this.id = id;
        this.skillname = skillname;
        this.skillname2 = skillname2;
        this.skillname3 = skillname3;
        this.smallCategories = smallCategories;
    }
}