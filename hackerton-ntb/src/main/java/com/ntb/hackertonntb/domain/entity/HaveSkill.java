package com.ntb.hackertonntb.domain.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HaveSkill {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skills skills;

    @Builder
    public HaveSkill(int id, Skills skills) {
        this.id = id;
        this.skills = skills;
    }
}