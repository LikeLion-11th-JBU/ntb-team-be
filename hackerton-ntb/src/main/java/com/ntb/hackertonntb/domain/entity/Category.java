package com.ntb.hackertonntb.domain.entity;

import com.ntb.hackertonntb.dto.UserDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id
    @GeneratedValue
    private int id;

    @Column(length = 45, nullable = false)
    private String categoryname;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User users;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categories", fetch = FetchType.LAZY)
    private List<SmallCategory> smallCategories = new ArrayList<>();

    @Builder
    public Category(int id, String categoryname, User users) {
        this.id = id;
        this.categoryname = categoryname;
        this.users = users;
    }
}
