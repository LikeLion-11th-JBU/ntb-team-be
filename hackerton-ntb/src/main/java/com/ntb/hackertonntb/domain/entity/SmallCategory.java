package com.ntb.hackertonntb.domain.entity;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
