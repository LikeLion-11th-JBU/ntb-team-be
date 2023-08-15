package com.ntb.hackertonntb.domain.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WantSkill {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "skills_id", nullable = false)
    private Skills wantSkills;

    @Builder
    public WantSkill(Skills wantSkills) {
        this.wantSkills = wantSkills;
    }


}