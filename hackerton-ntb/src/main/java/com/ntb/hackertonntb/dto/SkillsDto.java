package com.ntb.hackertonntb.dto;

import com.ntb.hackertonntb.domain.entity.*;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class SkillsDto {
    private int id;
    private String skillname;

    public Skills toEntity(){
        Skills build = Skills.builder()
                .id(id)
                .skillname(skillname)
                .build();
        return build;
    }

    @Builder
    public SkillsDto(int id, String skillname){
        this.id = id;
        this.skillname = skillname;
    }
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "skills")
    private List<HaveSkill> haveSkill;

    @OneToMany(mappedBy = "skills")
    private List<WantSkill> wantSkill;
}