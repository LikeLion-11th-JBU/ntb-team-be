package com.ntb.hackertonntb.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Skills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skills_id")
    private int id;

    @Column(length = 45, nullable = false)
    private String skillname;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "smallcategory_id")
    private SmallCategory smallCategories;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "haveSkills", fetch = FetchType.LAZY)
    private List<HaveSkill> haveSkills = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "wantSkills", fetch = FetchType.LAZY)
    private List<WantSkill> wantSkills = new ArrayList<>();

    @Builder
    public Skills(String skillname, SmallCategory smallCategories,
                  List<HaveSkill> haveSkills, List<WantSkill> wantSkills) {  // 생성자에 리스트들 추가
        this.skillname = skillname;
        this.smallCategories = smallCategories;
        this.haveSkills = haveSkills;
        this.wantSkills = wantSkills;
    }
}
