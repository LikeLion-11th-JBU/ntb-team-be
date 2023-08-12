package com.ntb.hackertonntb.dto;


import com.ntb.hackertonntb.domain.entity.Category;
import com.ntb.hackertonntb.domain.entity.Skills;
import com.ntb.hackertonntb.domain.entity.SmallCategory;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SmallCategoryDto {
        private int id;

        @NotBlank(message = "세부 카테고리는 필수 선택 값 입니다.")
        private String smallcategoryname;

        private Category categories;

        private List<Skills> skills;

        public SmallCategory toEntity(Category categories){
            SmallCategory build = SmallCategory.builder()
                    .smallcategoryname(smallcategoryname)
                    .categories(categories)
                    .skills(skills)
                    .build();
            return build;
        }
        @Builder
        public SmallCategoryDto(String smallcategoryname, Category categories, List<Skills> skills ){
            this.id = id;
            this.smallcategoryname = smallcategoryname;
            this.categories = categories;
            this.skills = skills;
        }
    }
