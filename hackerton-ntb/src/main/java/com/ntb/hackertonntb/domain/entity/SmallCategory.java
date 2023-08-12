package com.ntb.hackertonntb.domain.entity;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SmallCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "smallcategory_id")
    private int id;

    @Column(length = 45, nullable = false)
    private String smallcategoryname;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category categories;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "smallCategories", fetch = FetchType.LAZY)
    private List<Skills> skills = new ArrayList<>();

    @Builder
    public SmallCategory(String smallcategoryname, Category categories, List<Skills> skills) {
        this.smallcategoryname = smallcategoryname;
        this.categories = categories;
        this.skills = skills;
    }

}
