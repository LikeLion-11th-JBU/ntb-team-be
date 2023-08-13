package com.ntb.hackertonntb.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HaveSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "skills_id")
    private Skills haveSkills;

    @Builder
    public HaveSkill(Skills haveSkills) {
        this.haveSkills = haveSkills;

    }

}