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
    @GeneratedValue
    private int id;

    @Column(length = 45, nullable = false)
    private String skillname;

    @Column(length = 45, nullable = false)
    private String skillname2;

    @Column(length = 45, nullable = false)
    private String skillname3;

    @ManyToOne
    @JoinColumn(name = "smallcategory_id")
    private SmallCategory smallCategories;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "skills", fetch = FetchType.LAZY)
    private List<HaveSkill> haveSkills = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "skills", fetch = FetchType.LAZY)
    private List<WantSkill> wantSkills = new ArrayList<>();

    @Builder
    public Skills(int id, String skillname, String skillname2, String skillname3, SmallCategory smallCategories) {
        this.id = id;
        this.skillname = skillname;
        this.skillname2 = skillname2;
        this.skillname3 = skillname3;
        this.smallCategories = smallCategories;
    }
}
