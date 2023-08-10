package com.ntb.hackertonntb.dto;

import com.ntb.hackertonntb.domain.entity.Category;
import com.ntb.hackertonntb.domain.entity.User;
import com.ntb.hackertonntb.domain.repository.UserRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class CategoryDto {
    private int id;

    @NotBlank(message = "카테고리는 필수 선택 값 입니다.")
    private String categoryname;

    private User users;

    public Category toEntity(){
        Category build = Category.builder()
                .id(id)
                .categoryname(categoryname)
                .build();
        return build;
    }
    @Builder
    public CategoryDto(int id, String categoryname, User users){
        this.id = id;
        this.categoryname = categoryname;
        this.users = users;
    }
}
