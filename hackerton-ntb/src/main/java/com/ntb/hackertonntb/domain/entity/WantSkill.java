package com.ntb.hackertonntb.domain.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WantSkill {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "skills_id", nullable = false)
    private Skills wantSkills;

    @Builder
    public WantSkill(Skills wantSkills) {
        this.wantSkills = wantSkills;
    }
}