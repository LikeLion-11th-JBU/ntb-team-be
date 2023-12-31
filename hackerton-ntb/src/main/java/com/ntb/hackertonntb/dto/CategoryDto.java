package com.ntb.hackertonntb.dto;

import com.ntb.hackertonntb.domain.entity.Category;
import com.ntb.hackertonntb.domain.entity.SmallCategory;
import com.ntb.hackertonntb.domain.entity.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import com.ntb.hackertonntb.domain.entity.Skills;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class CategoryDto {
    private int id;

    @NotBlank(message = "카테고리는 필수 선택 값 입니다.")
    private String categoryname;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User users;

    @OneToMany(mappedBy = "categories")
    private List<SmallCategory> smallCategories;

    public Category toEntity(User users){
        Category build = Category.builder()
                .categoryname(categoryname)
                .users(users)
                .smallCategories(smallCategories)
                .build();
        return build;
    }
    @Builder
    public CategoryDto(int id,
                       String categoryname,
                       User users,
                       List<SmallCategory> smallCategories
    ){
        this.id = id;
        this.categoryname = categoryname;
        this.users = users;
        this.smallCategories = smallCategories;
    }

}
