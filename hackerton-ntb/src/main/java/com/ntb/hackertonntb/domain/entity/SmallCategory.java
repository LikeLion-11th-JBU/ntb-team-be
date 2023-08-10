package com.ntb.hackertonntb.domain.entity;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SmallCategory {

    @Id
    @GeneratedValue
    private int id;

    @Column(length = 45, nullable = false)
    private String smallcategoryname;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category categories;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "smallCategories", fetch = FetchType.LAZY)
    private List<Skills> skills = new ArrayList<>();

    @Builder
    public SmallCategory(int id, String smallcategoryname, Category categories) {
        this.id = id;
        this.smallcategoryname = smallcategoryname;
        this.categories = categories;
    }

}
