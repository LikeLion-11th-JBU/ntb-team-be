package com.ntb.hackertonntb.dto;

import com.ntb.hackertonntb.domain.entity.Category;
import com.ntb.hackertonntb.domain.entity.Skills;
import com.ntb.hackertonntb.domain.entity.User;
import lombok.*;

import javax.persistence.OneToMany;
import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class CategoryDto {
    private int id;
    private String categoryname;

    public Category toEntity(){
        Category build = Category.builder()
                .id(id)
                .categoryname(categoryname)
                .build();
        return build;
    }

    @Builder
    public CategoryDto(int id, String categoryname){
        this.id = id;
        this.categoryname = categoryname;
    }

    @OneToMany(mappedBy = "category")
    private List<Skills> skills;

    @OneToMany(mappedBy = "category")
    private List<User> users;
}
